package com.cts.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.model.Product;

public interface ProductRepo extends JpaRepository<Product, String> {

}
