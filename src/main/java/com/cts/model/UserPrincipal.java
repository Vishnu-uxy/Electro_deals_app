package com.cts.model;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{
	
	@Autowired
	private User user;
	
	public UserPrincipal(User user) {
		this.user=user;
	}

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
////		return user.getRoles().stream() .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) .collect(Collectors.toList());
//	}
	@Override public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRoles()));
		}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}



}
