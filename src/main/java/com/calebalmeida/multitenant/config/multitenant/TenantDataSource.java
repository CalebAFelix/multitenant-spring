package com.calebalmeida.multitenant.config.multitenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class TenantDataSource {

	private Map<String, DataSource> dataSources = new HashMap<>();
	
	@Autowired
	private DataSourceConfigRepository configRepo;
	
	public DataSource getDataSource(String name) {
		DataSource dataSource = this.dataSources.get(name);
		if(dataSource == null) {
			dataSource = createDataSource(name);
		}
		return dataSource;
	}
	
	@PostConstruct
	public Map<String, DataSource> getAll() {
		Map<String, DataSource> dataSources = new HashMap<>();
		List<DataSourceConfig> configList = configRepo.findByInitialized(true);
		for(DataSourceConfig config : configList) {
			DataSource dataSource = this.mountDataSource(config);
			dataSources.put(config.getName(), dataSource);
		}
		return dataSources;
	}
	
	private DataSource createDataSource(String name) {
		DataSourceConfig config = configRepo.findByNameAndInitialized(name, true);
		DataSource dataSource = this.mountDataSource(config);
		if (dataSource != null) {
			dataSources.put(config.getName(), dataSource);
			return dataSource;
		}
		return null;
	}
	
	private DataSource mountDataSource(DataSourceConfig config) {
		if(config == null) return null;
		DataSource ds = DataSourceBuilder
							.create()
							.driverClassName(config.getDriverClassName())
							.username(config.getUsername())
							.password(config.getPassword())
							.url(config.getUrl())
							.build();
		return ds;

	}
}
