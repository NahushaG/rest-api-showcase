package com.example.pagination.util;

import com.example.pagination.model.Product;
import com.example.pagination.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class ProductGenerator implements CommandLineRunner {
    private final ProductRepository productRepository;

    private final Random random = new Random();

    private final String[] categories = {"Electronics", "Books", "Clothing", "Food", "Toys"};

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            for (int i = 1; i <= 50; i++) {
                String name = "Product " + i;
                String category = categories[random.nextInt(categories.length)];
                Double price = Math.round(random.nextDouble() * 10000) / 100.0; // price between 0.00 and 100.00

                Product product = Product.builder()
                        .name(name)
                        .category(category)
                        .price(price)
                        .build();
                productRepository.save(product);
            }
            System.out.println("50 Products saved successfully!");
        } else {
            System.out.println("There is already a product with that name!");
        }

    }
}
