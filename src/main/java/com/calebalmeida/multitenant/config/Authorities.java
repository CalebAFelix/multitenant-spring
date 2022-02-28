package com.calebalmeida.multitenant.config;

public enum Authorities {

	USER("USER"),
	ADMIN("ADMIN");
	
	private String name;
	
	private Authorities(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
