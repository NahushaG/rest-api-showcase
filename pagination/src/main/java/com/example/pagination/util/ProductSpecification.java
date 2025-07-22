package com.example.pagination.util;

import com.example.pagination.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> cb.equal(root.get("name"), name);
    }
}
