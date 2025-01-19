package com.cts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

	@Id
	@Column(unique = true)
	private String product_id;

	@Column

	private String Description;
	@Column

	private int price;
	@Column

	private int discount;
	
	@Lob
    @Column
    private byte[] image;

}
