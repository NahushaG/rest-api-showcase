package com.example.pagination;

import com.example.pagination.model.Product;
import com.example.pagination.repository.ProductRepository;
import com.example.pagination.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc

public class ProductAPIIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;


    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        List<Product> products = List.of(
                new Product(null, "Prod1", "Cat1", 100.0),
                new Product(null, "Prod2", "Cat2", 200.0),
                new Product(null, "Prod2", "Cat2", 200.0),
                new Product(null, "Prod2", "Cat1", 200.0),
                new Product(null, "Prod2", "Cat2", 200.0),
                new Product(null, "Prod2", "Cat3", 200.0),
                new Product(null, "Prod2", "Cat2", 200.0));
        productRepository.saveAll(products);
    }

    @Test
    void test_findAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRecords").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].name").value("Prod1"));
    }

    @Test
    void test_findALLByPaginationSorting() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/products/pagination/0/3/category"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRecords").value(3));
    }
}
