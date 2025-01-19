package com.cts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {

	@NotBlank(message = "productId can not be blank")

	private String productId;

	@NotBlank(message = "description can not be blank")
	private String description;

	@NotNull(message = "price can not be null")
	@Min(value = 0, message = "Price must be a positive number")
	private int price;

	@NotNull(message = "price can not be null")
	@Min(value = 0, message = "Price must be a positive number")
	private int discount;
	
	
}
