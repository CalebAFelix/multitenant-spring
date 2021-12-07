package com.calebalmeida.multitenant.config.multitenant;

public class TenantContext {

	private static ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();
	
	public static String getCurrentTenant() {
		return currentTenant.get();
	}
	
	public static void setCurrentTenant(String tenant) {
		System.out.println("configurando tenant: " + tenant);
		currentTenant.set(tenant);
	}
	
	public static void clear() {
		System.out.println("limpando tenant: " + getCurrentTenant());
		currentTenant.set(null);
	}
	
}
