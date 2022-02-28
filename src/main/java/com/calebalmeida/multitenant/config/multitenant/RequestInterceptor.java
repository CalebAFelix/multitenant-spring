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
	public void postHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView modelAndView
			) throws Exception {
		TenantContext.clear();
	}
	
}
