package com.calebalmeida.multitenant.config.multitenant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "data_source_config", schema="public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceConfig implements Serializable{
	private static final long serialVersionUID = -8593967759874796609L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String url;
    private String username;
    private String password;
    
    @Column(name="driver_class_name")
    private String driverClassName;
    private boolean initialized;
}
