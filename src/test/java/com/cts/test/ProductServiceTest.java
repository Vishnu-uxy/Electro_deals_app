package com.cts.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.dto.ProductResponseDto;
import com.cts.model.Product;
import com.cts.repo.ProductRepo;
import com.cts.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProduct_id("1");
        product.setDescription("Sample product");
        product.setPrice(100);
        product.setDiscount(10);
    }

    @Test
    public void testAddProduct_Success() {
        when(productRepo.save(product)).thenReturn(product);
        Product savedProduct = productService.addProduct(product);
        assertEquals(product, savedProduct);
        verify(productRepo, times(1)).save(product);
    }

    @Test
    public void testViewById_ProductExists() {
        when(productRepo.findById(product.getProduct_id())).thenReturn(Optional.of(product));
        Optional<ProductResponseDto> foundProduct = productService.viewById(product.getProduct_id());
        
        // Convert product to ProductResponseDto
        ProductResponseDto expectedProductDto = new ProductResponseDto();
        expectedProductDto.setProductId(product.getProduct_id());
        expectedProductDto.setDescription(product.getDescription());
        expectedProductDto.setPrice(product.getPrice());
        expectedProductDto.setDiscount(product.getDiscount());
        expectedProductDto.setImage(null); // Assuming image is null for this test

        assertEquals(Optional.of(expectedProductDto), foundProduct);
    }

    @Test
    public void testViewById_ProductDoesNotExist() {
        // Arrange
        when(productRepo.findById(product.getProduct_id())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            productService.viewById(product.getProduct_id()).orElseThrow(() -> new RuntimeException("Product does not exist"));
        });

        // Verify
        verify(productRepo, times(1)).findById(product.getProduct_id());
    }

    @Test
    public void testUpdateProduct_ProductExists() {
        when(productRepo.findById(product.getProduct_id())).thenReturn(Optional.of(product));
        when(productRepo.save(product)).thenReturn(product);
        Product updatedProduct = productService.updateProduct(product.getProduct_id(), product);
        assertEquals(product, updatedProduct);
    }

    @Test
    public void testUpdateProduct_ProductDoesNotExist() {
        when(productRepo.findById(product.getProduct_id())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(product.getProduct_id(), product);
        });
    }

    @Test
    public void testDeleteProduct() {
        productService.deleteProduct(product.getProduct_id());
        verify(productRepo, times(1)).deleteById(product.getProduct_id());
    }
}