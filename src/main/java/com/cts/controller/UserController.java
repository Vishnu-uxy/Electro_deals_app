package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.UserDto;
import com.cts.model.User;
import com.cts.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public User registerUser(@Valid @RequestBody UserDto userDto) {
		User user = new User(); 
		user.setName(userDto.getName()); 
		user.setEmail(userDto.getEmail()); 
		user.setPassword(userDto.getPassword()); 
		return userService.addUser(user);
	}
	

}
