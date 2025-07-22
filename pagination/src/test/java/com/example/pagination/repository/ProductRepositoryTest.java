package com.example.pagination.repository;

import com.example.pagination.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Optional in case using real DB
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindAllProductCategory() {
        Product product = new Product(null, "Test Product", "Test Category", 99.99);
        productRepository.save(product);
        List<Product> fetchedProduct= productRepository.findAll();
        Assertions.assertEquals(product, fetchedProduct.get(0));
    }

    @Test
    void testFindWithPagination() {
        List<Product> products = List.of(
                new Product(null, "Prod1", "Cat1", 100.0),
                new Product(null, "Prod2", "Cat2", 200.0),
                new Product(null, "Prod2", "Cat2", 200.0),
                new Product(null, "Prod2", "Cat1", 200.0),
                new Product(null, "Prod2", "Cat2", 200.0),
                new Product(null, "Prod2", "Cat3", 200.0),
                new Product(null, "Prod2", "Cat2", 200.0));
        productRepository.saveAll(products);
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Product>productPage =  productRepository.findAll(pageRequest);

        Assertions.assertEquals(products.size(), productPage.getTotalElements());
    }

}
