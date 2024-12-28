package com.cts.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.exceptions.UserNameNotFoundException;
import com.cts.model.User;
import com.cts.model.UserPrincipal;
import com.cts.repo.UserRepo;
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepo.findByEmail(email);
		
		if(user==null) {
			throw new UserNameNotFoundException("user not found");
		}
		return new UserPrincipal(user);
	}

}
