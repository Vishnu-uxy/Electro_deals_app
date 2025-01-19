package com.cts.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.ProductResponseDto;
import com.cts.dto.UserDto;
import com.cts.dto.UserLoginDto;
import com.cts.model.Product;
import com.cts.model.User;
import com.cts.service.JwtService;
import com.cts.service.ProductService;
import com.cts.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ProductService productService;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@PostMapping("/register")
	public User registerUser(@Valid @RequestBody UserDto userDto) {
		logger.info("Registering user with email: {}", userDto.getEmail());
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(encoder.encode(userDto.getPassword()));
		User regisUser = userService.addUser(user);
		logger.info("Successfully registered user with email: {}", userDto.getEmail());
		return regisUser;
	}

	@PostMapping("/login")
	public String login(@Valid @RequestBody UserLoginDto userLoginDto) {

		logger.info("User login attempt with email: {}", userLoginDto.getEmail());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(userLoginDto.getEmail());
			logger.info("User logged in successfully, generated token for email: {}", userLoginDto.getEmail());
			return token;
		} else {
			logger.warn("Login failed for email: {}", userLoginDto.getEmail());
			return "Login Failed";
		}

	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/viewProducts")
	public List<ProductResponseDto> viewAllProduct() {
		logger.info("Fetching all products");
		return productService.viewAllProduct();
	}


}
