package com.cts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.exceptions.UserAlreadyExistException;
import com.cts.model.User;
import com.cts.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;

	public User addUser(User user) {
		if(userRepo.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistException("user already exists");
		}
		else return userRepo.save(user);
	}
	public List<User> findAllUser(){
		return userRepo.findAll();
	}
	public Optional<User> findUserById(long id){
		User user=userRepo.findById(id).orElse(null);
		if(user!=null) {
			return userRepo.findById(id);
		}
		else {
			throw new RuntimeException("User does not exist with this id");
		}
	}
	public void deleteUser(long id) {
		userRepo.deleteById(id);
	}

}
