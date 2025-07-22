package com.example.pagination.controller;

import com.example.pagination.dto.ProductResponseDTO;
import com.example.pagination.model.Product;
import com.example.pagination.repository.ProductRepository;
import com.example.pagination.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ProductResponseDTO<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return new ProductResponseDTO<>(products, products.size());
    }

    @GetMapping("/{field}")
    public ProductResponseDTO<List<Product>> findAllProductCategory(@PathVariable String field) {

        List<Product> products = productService.findProductWithSorting(field);
        return new ProductResponseDTO<>(products, products.size());

    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public ProductResponseDTO<Page<Product>> findAllProductCategory(@PathVariable int offset, @PathVariable int pageSize) {

        Page<Product> products = productService.findProductWithPagination(offset, pageSize);
        return new ProductResponseDTO<>(products, products.getSize());

    }

    @GetMapping("/pagination/{offset}/{pageSize}/{fieldName}")
    public ProductResponseDTO<Page<Product>> findAllProductCategory(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String fieldName) {

        Page<Product> products = productService.findProductWithPaginationAndSortBy(offset, pageSize, fieldName);
        return new ProductResponseDTO<>(products, products.getSize());

    }
}
