package com.cts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cts.dto.ProductDto;
import com.cts.dto.ProductResponseDto;
import com.cts.dto.UserLoginDto;
import com.cts.exceptions.UserNameNotFoundException;
import com.cts.model.Product;
import com.cts.model.User;
import com.cts.service.JwtService;
import com.cts.service.ProductService;
import com.cts.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public Product addProduct(@Valid @RequestParam("product") String productJson, @RequestParam("image") MultipartFile image) throws IOException {
        logger.info("Adding new product");
        ProductDto productDto = new ObjectMapper().readValue(productJson, ProductDto.class); // Deserialize JSON to ProductDto
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setDiscount(productDto.getDiscount());
        product.setPrice(productDto.getPrice());
        product.setProduct_id(productDto.getProductId());
        product.setImage(image.getBytes()); // Set image bytes
        Product addedProduct = productService.addProduct(product);
        logger.info("Successfully added product with ID: {}", product.getProduct_id());
        return addedProduct;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewProducts")
    public List<ProductResponseDto> viewAllProduct() {
        logger.info("Fetching all products");
        return productService.viewAllProduct();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/viewProduct/{id}")
    public ProductResponseDto viewProductById(@PathVariable String id) {
        return productService.viewById(id).orElseThrow(() -> new RuntimeException("Product does not exist"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @Valid @RequestParam("product") String productJson, @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        logger.info("Updating product with ID: {}", productId);
        ProductDto productDto = new ObjectMapper().readValue(productJson, ProductDto.class); // Deserialize JSON to ProductDto
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setDiscount(productDto.getDiscount());
        product.setPrice(productDto.getPrice());
        product.setProduct_id(productDto.getProductId());

        // Set image bytes if a new image is provided
        if (image != null && !image.isEmpty()) {
            product.setImage(image.getBytes());
        }

        Product updatedProduct = productService.updateProduct(productId, product);
        logger.info("Successfully updated product with ID: {}", productId);
        return ResponseEntity.ok(updatedProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct/{product_id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String product_id) {
        logger.info("Deleting product with ID: {}", product_id);
        productService.deleteProduct(product_id);
        logger.info("Successfully deleted product with ID: {}", product_id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Attempting to find user with ID: {}", id);
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            logger.info("User found with ID: {}", id);
            return ResponseEntity.ok(user.get());
        }
        logger.warn("User not found with ID: {}", id);
        throw new UserNameNotFoundException("User not found with ID: " + id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            logger.info("User successfully deleted with ID: {}", id);
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        logger.warn("User not found with ID: {}", id);
        throw new UserNameNotFoundException("User not found with Id " + id);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UserLoginDto userLoginDto) {
        logger.info("Admin login attempt with email: {}", userLoginDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(userLoginDto.getEmail());
            logger.info("Admin logged in successfully, generated token for email: {}", userLoginDto.getEmail());
            return token;
        } else {
            logger.warn("Login failed for email: {}", userLoginDto.getEmail());
            return "Login Failed";
        }
    }
}