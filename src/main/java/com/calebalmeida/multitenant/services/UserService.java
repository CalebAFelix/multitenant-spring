package com.calebalmeida.multitenant.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calebalmeida.multitenant.entities.Role;
import com.calebalmeida.multitenant.entities.User;
import com.calebalmeida.multitenant.repositories.RoleRepository;
import com.calebalmeida.multitenant.repositories.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;	
	

	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}
	
	public void addRoleToUser(String username, String roleName) {
		User user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}
	
	public User find(String userName) {
		return userRepository.findByUsername(userName);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
}
