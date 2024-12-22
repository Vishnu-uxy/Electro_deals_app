package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.ProductDto;
import com.cts.model.Product;
import com.cts.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/addProduct")
	public Product addProduct(@Valid @RequestBody ProductDto productDto) {
		Product product=new Product();
		product.setDescription(productDto.getDescription());
		product.setDiscount(productDto.getPrice());
		product.setPrice(productDto.getPrice());
		product.setProduct_id(productDto.getProductId());
		return productService.addProduct(product);
	}
	
	@GetMapping("/viewProducts")
	public List<Product> viewAllProduct(){
		return productService.viewAllProduct();
	}
	
	@PutMapping("/updateProduct/{product_id}") 
	public ResponseEntity<Void> updateProduct(@PathVariable String product_id, @Valid @RequestBody ProductDto productDto) {
		Product product=new Product();
		product.setDescription(productDto.getDescription());
		product.setDiscount(productDto.getPrice());
		product.setPrice(productDto.getPrice());
		product.setProduct_id(productDto.getProductId());
		productService.updateProduct(product_id, product); 
		return ResponseEntity.noContent().build(); 
		}
	@DeleteMapping("/deleteProduct/{product_id}") 
	public ResponseEntity<Void> deleteProduct(@PathVariable String product_id) { 
		productService.deleteProduct(product_id); 
		return ResponseEntity.noContent().build();
		}
}
