package com.example.pagination.service;

import com.example.pagination.model.Product;
import com.example.pagination.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findProductWithSorting(String field) {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public Page<Product> findProductWithPagination(int offset, int pagesize) {
        return productRepository.findAll(PageRequest.of(offset, pagesize));
    }

    public Page<Product> findProductWithPaginationAndSortBy(int offset, int pagesize, String field) {
        return productRepository.findAll(PageRequest.of(offset, pagesize).withSort(Sort.by(Sort.Direction.ASC, field)));
    }
}
