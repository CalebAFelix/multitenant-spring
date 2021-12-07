package com.calebalmeida.multitenant.config.multitenant.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import com.calebalmeida.multitenant.config.multitenant.TenantContext;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver{

	private String defaultTenant = "default";
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenant = TenantContext.getCurrentTenant();
		if(tenant != null) return tenant;
		return defaultTenant;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
