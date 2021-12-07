package com.calebalmeida.multitenant.config.multitenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceConfigRepository extends JpaRepository<DataSourceConfig, Long>{

	DataSourceConfig findByName(String name);
	
	DataSourceConfig findByNameAndInitialized(String name, Boolean initialized);
	
	List<DataSourceConfig> findByInitialized(Boolean initialized);
}
