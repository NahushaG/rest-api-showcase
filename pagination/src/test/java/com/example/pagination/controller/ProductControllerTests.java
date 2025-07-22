package com.example.pagination.controller;

import com.example.pagination.model.Product;
import com.example.pagination.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Comparator;
import java.util.List;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_findAllProduct() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "Prod1", "Cat1", 100.0),
                new Product(2L, "Prod2", "Cat2", 200.0)
        );
        Mockito.when(productService.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRecords").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].name").value("Prod1"));

    }

    @Test
    public void test_findProductWithSorting() throws Exception {
        String field = "category";
        List<Product> products = List.of(
                new Product(1L, "Prod1", "Cat1", 100.0),
                new Product(2L, "Prod2", "Cat2", 200.0),
                new Product(3L, "Prod2", "Cat2", 200.0),
                new Product(4L, "Prod2", "Cat1", 200.0),
                new Product(5L, "Prod2", "Cat2", 200.0),
                new Product(6L, "Prod2", "Cat3", 200.0),
                new Product(7L, "Prod2", "Cat2", 200.0));
        Mockito.when(productService.findProductWithSorting(field)).thenReturn(products.stream().sorted(Comparator.comparing(Product::getCategory)).toList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/" + field))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRecords").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[1].category").value("Cat1"));

    }

    @Test
    public void test_findProductWithPagination() throws Exception {
        int offset = 0;
        int pageSize = 2;
        List<Product> products = List.of(
                new Product(1L, "Prod1", "Cat1", 100.0),
                new Product(2L, "Prod2", "Cat2", 200.0),
                new Product(3L, "Prod2", "Cat2", 200.0),
                new Product(4L, "Prod2", "Cat1", 200.0),
                new Product(5L, "Prod2", "Cat2", 200.0),
                new Product(6L, "Prod2", "Cat3", 200.0),
                new Product(7L, "Prod2", "Cat2", 200.0));
        Page<Product> page = new PageImpl<>(products.stream().limit(pageSize).toList());
        Mockito.when(productService.findProductWithPagination(offset, pageSize)).thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/pagination/" + offset + "/" + pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRecords").value(2));

    }

}
