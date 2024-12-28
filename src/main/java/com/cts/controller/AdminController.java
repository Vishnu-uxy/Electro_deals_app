package com.cts.controller;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private ProductService productService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addProduct")
	public Product addProduct(@Valid @RequestBody ProductDto productDto) {
		logger.info("Adding new product with ID: {}", productDto.getProductId());
		Product product=new Product();
		product.setDescription(productDto.getDescription());
		product.setDiscount(productDto.getDiscount());
		product.setPrice(productDto.getPrice());
		product.setProduct_id(productDto.getProductId());
		return productService.addProduct(product);
	}
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/viewProducts")
	public List<Product> viewAllProduct(){
		logger.info("Fetching all products");
		return productService.viewAllProduct();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateProduct/{productId}") 
	public ResponseEntity<Product> updateProduct(@PathVariable String productId, @Valid @RequestBody ProductDto productDto) {
		logger.info("Updating product with ID: {}", productId);
		Product product=new Product();
		product.setDescription(productDto.getDescription());
		product.setDiscount(productDto.getDiscount());
		product.setPrice(productDto.getPrice());
		product.setProduct_id(productDto.getProductId());
		Product updatedProduct = productService.updateProduct(productId, product);
		return ResponseEntity.ok(updatedProduct); 
		}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteProduct/{product_id}") 
	public ResponseEntity<Void> deleteProduct(@PathVariable String product_id) { 
		logger.info("Deleting product with ID: {}", product_id);
		productService.deleteProduct(product_id); 
		return ResponseEntity.noContent().build();
		}
	
//	@PreAuthorize("hasRole('ADMIN')")
//	@GetMapping("/csrf-token")
//	public CsrfToken getCsrfToken(HttpServletRequest request) {
//		return (CsrfToken)request.getAttribute("_csrf");
//	}
}
