package com.cts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="product")
public class Product {
    
	
	@Id
	@Column(unique=true)
	private String product_id;
	
	@Column
	
	private String Description;
	@Column 
	
	private int price;
	@Column 
	
	private int discount;
	
}
