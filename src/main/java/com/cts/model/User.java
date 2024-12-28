package com.cts.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data

@Table(name="user")
public class User{
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
 
	
	@Column
	private String name;
	
	
	
	@Column(unique=true)
	private String email;
	
	
	@Column(nullable = false)
	private String password;
	
	@Column 
	private String roles; 
//	public List<String> getRoles() { 
//		return Arrays.asList(roles.split(",")); 
//		} 
//	public void setRoles(List<String> roles) { 
//		this.roles = roles.stream().collect(Collectors.joining(",")); 
//		}
//	
	
	
}
