package com.calebalmeida.multitenant.config.multitenant.hibernate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.calebalmeida.multitenant.config.multitenant.TenantDataSource;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl{
	private static final long serialVersionUID = -6975879173157386900L;

	private static final String DEFAULT_TENANT_ID = "default";
	
	@Autowired
	private DataSource defaultDS;
	
	@Autowired
	private ApplicationContext context;

	private Map<String, DataSource> map = new HashMap<>();
	
	boolean init = false;
	
	@PostConstruct
	public void load() {
		map.put(DEFAULT_TENANT_ID, defaultDS);
	}
	
	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(DEFAULT_TENANT_ID);
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		if(!init) {
			init = true;
			TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
			map.putAll(tenantDataSource.getAll());
		}
		DataSource ds = map.get(tenantIdentifier) != null? map.get(tenantIdentifier) : map.get(DEFAULT_TENANT_ID);
 		return ds;
	}
	
	public boolean verifyDataSource(String tenantIdentifier) {
		//verificar se o tenant está no hasmap
		DataSource ds = map.get(tenantIdentifier);
		if(ds != null) return true;
		
		//caso não esteja verifica na base
		TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
		DataSource dsNew = tenantDataSource.getDataSource(tenantIdentifier);
		if(dsNew != null) {			
			map.put(tenantIdentifier, dsNew);
			return true;
		}
		
		//caso contrário retorna falso
		return false;
	}

}
