package com.cts.dto;


import lombok.Data;

@Data
public class ProductResponseDto {

    private String productId;
    private String description;
    private int price;
    private int discount;
    private String image; 
}
