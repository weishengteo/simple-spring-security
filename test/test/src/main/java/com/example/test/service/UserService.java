package com.example.test.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.test.entity.User;

public interface UserService {
	
	public User addUser(User user);
	
	public UserDetails loadUserByUsername(String username);
}
