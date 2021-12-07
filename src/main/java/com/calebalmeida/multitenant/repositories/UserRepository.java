package com.calebalmeida.multitenant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calebalmeida.multitenant.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
}
