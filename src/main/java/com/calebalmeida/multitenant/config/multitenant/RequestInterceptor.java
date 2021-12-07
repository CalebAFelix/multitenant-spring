package com.calebalmeida.multitenant.config.multitenant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.calebalmeida.multitenant.config.multitenant.hibernate.DataSourceBasedMultiTenantConnectionProviderImpl;

@Component
public class RequestInterceptor implements HandlerInterceptor{

	@Autowired
	DataSourceBasedMultiTenantConnectionProviderImpl dsbmtcpi;
	
	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler
			) throws Exception{
		String tenantID = request.getHeader("X-TenantId");
		
		if(tenantID == null) {
			response.getWriter().write("X-TenantId not presnet in the Request Header");
			response.setStatus(400);
			return false;
		}
		
		boolean ds = dsbmtcpi.verifyDataSource(tenantID);
		if (ds == false) {
			response.getWriter().write("X-tenantId does not represent any authorized tenant in our system");
			response.setStatus(400);
			return false;
		}
		
		TenantContext.setCurrentTenant(tenantID);
		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView modelAndView
			) throws Exception {
		TenantContext.clear();
	}
	
}
