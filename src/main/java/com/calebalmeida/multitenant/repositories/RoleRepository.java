package com.calebalmeida.multitenant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calebalmeida.multitenant.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
	
}
