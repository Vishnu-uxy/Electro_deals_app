package com.cts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.model.Product;

import com.cts.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	public List<Product> viewAllProduct() {
		return productRepo.findAll();
	}

	public Optional<Product> viewById(String product_id) {
		return Optional.ofNullable(
				productRepo.findById(product_id).orElseThrow(() -> new RuntimeException("Product does not exist")));
	}

	public Product updateProduct(String product_id, Product product) {
		Product pro = productRepo.findById(product_id).orElse(null);
		if (pro != null) {
			return productRepo.save(product);
		} else {
			throw new RuntimeException("Product does not exist");
		}
	}

	public void deleteProduct(String product_id) {
		productRepo.deleteById(product_id);
	}

}
