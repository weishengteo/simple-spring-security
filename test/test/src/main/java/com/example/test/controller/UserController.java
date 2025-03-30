package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.dto.AuthRequest;
import com.example.test.entity.User;
import com.example.test.service.UserService;
import com.example.test.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}
	
	@PostMapping("/add")
	public User add(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@PostMapping("/generateToken")
	public String generateToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
	}
	
	@GetMapping("/userOnly")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userOnly() {
		return "This is a user limited service";
	}
	
	@GetMapping("/adminOnly")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminOnly() {
		return "This is a admin limited service";
	}
}
