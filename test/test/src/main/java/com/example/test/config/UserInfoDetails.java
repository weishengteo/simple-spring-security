package com.example.test.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.test.entity.User;

public class UserInfoDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String username; // Changed from 'name' to 'email' for clarity
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = List.of(user.getRoles().split(","))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
	public String getPassword() {
		return password;
	}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}