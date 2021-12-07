package com.calebalmeida.multitenant.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.calebalmeida.multitenant.entities.Role;
import com.calebalmeida.multitenant.services.UserService;

@RestController
@RequestMapping("roles")
public class RoleResource {
	
	@Autowired
	UserService userService;
	
	public ResponseEntity<Void> insert(@RequestBody Role role) {
		role = userService.saveRole(role);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(role.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
