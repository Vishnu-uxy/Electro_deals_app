package com.cts.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.ProductResponseDto;
import com.cts.model.Product;

import com.cts.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	public List<ProductResponseDto> viewAllProduct() {
        return productRepo.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<ProductResponseDto> viewById(String product_id) {
        return productRepo.findById(product_id).map(this::convertToDto);
    }

	public Product updateProduct(String product_id, Product product) {
		Product pro = productRepo.findById(product_id).orElse(null);
		if (pro != null) {
			product.setProduct_id(product_id);
			if (product.getImage() == null) {
                product.setImage(pro.getImage());
            }
			return productRepo.save(product);
		} else {
			throw new RuntimeException("Product does not exist");
		}
	}

	public void deleteProduct(String product_id) {
		productRepo.deleteById(product_id);
	}
	
	 private ProductResponseDto convertToDto(Product product) {
	        ProductResponseDto dto = new ProductResponseDto();
	        dto.setProductId(product.getProduct_id());
	        dto.setDescription(product.getDescription());
	        dto.setPrice(product.getPrice());
	        dto.setDiscount(product.getDiscount());
	        dto.setImage(product.getImage() != null ? Base64.getEncoder().encodeToString(product.getImage()) : null);  // Convert image to Base64
	        return dto;
	    }

}
