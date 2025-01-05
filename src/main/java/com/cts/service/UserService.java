package com.cts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.exceptions.UserAlreadyExistException;
import com.cts.model.User;
import com.cts.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	public User addUser(User user) {
		if (userRepo.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistException("user already exists");
		}
		user.setRoles("USER");
		return userRepo.save(user);
	}

	public List<User> findAllUser() {
		return userRepo.findAll();
	}

	public Optional<User> findUserById(long id) {
	    return userRepo.findById(id);
	}


	public void deleteUser(long id) {
		userRepo.deleteById(id);
	}

}
