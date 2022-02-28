package com.calebalmeida.multitenant.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.calebalmeida.multitenant.entities.Role;
import com.calebalmeida.multitenant.entities.User;
import com.calebalmeida.multitenant.repositories.RoleRepository;
import com.calebalmeida.multitenant.repositories.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService{
	
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  RoleRepository roleRepository;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) throw new UsernameNotFoundException("User " + username + " não foi encontrado");
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		org.springframework.security.core.userdetails.User userauth = new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), authorities);
		return userauth;
	}

	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
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
