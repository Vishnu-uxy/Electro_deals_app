package com.cts.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {

	@NotBlank
    
    private String productId;  

	@NotBlank
    private String description;  

    @NotNull
    @Min(value = 0, message = "Price must be a positive number")
    private int price;

    @NotNull
    @Min(value = 0, message = "Price must be a positive number")
    private int discount;
}
