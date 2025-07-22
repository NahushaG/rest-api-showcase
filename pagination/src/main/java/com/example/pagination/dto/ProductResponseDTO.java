package com.example.pagination.dto;

import com.example.pagination.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO<T> {
    T products;
    int totalRecords;
}
