package com.example.pagination.service;

import com.example.pagination.model.Product;
import com.example.pagination.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts() {
        List<Product> mockProducts = List.of(
                new Product(1L, "Prod1", "Cat1", 100.0),
                new Product(2L, "Prod2", "Cat2", 200.0)
        );

        Mockito.when(productRepository.findAll()).thenReturn(mockProducts);
        List<Product> result = productService.findAll();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Prod1", result.get(0).getName());

        Mockito.verify(productRepository, Mockito.times(1)).findAll();

    }
}
