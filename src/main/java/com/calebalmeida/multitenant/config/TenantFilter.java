package com.calebalmeida.multitenant.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.calebalmeida.multitenant.config.multitenant.TenantContext;
import com.calebalmeida.multitenant.config.multitenant.hibernate.DataSourceBasedMultiTenantConnectionProviderImpl;

public class TenantFilter extends GenericFilterBean {

	@Autowired
	DataSourceBasedMultiTenantConnectionProviderImpl dsbmtcpi;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Tenant Filter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String tenantID = httpRequest.getHeader("X-TenantId");
		
		if(tenantID == null) {
			httpResponse.getWriter().write("X-TenantId not presnet in the Request Header");
			httpResponse.setStatus(400);
			response = httpResponse;
			return;
		}
		
		if(dsbmtcpi == null){
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            dsbmtcpi = webApplicationContext.getBean(DataSourceBasedMultiTenantConnectionProviderImpl.class);
        }
		
		boolean ds = dsbmtcpi.verifyDataSource(tenantID);
		if (ds == false) {
			httpResponse.getWriter().write("X-tenantId does not represent any authorized tenant in our system");
			httpResponse.setStatus(400);
			response = httpResponse;
			return;
		}
		
		TenantContext.setCurrentTenant(tenantID);
		chain.doFilter(request, response);
	}

}
