package com.example.product.dto;

import com.example.product.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest dto) {
        Product product = new Product();
        product.name = dto.name;
        product.description = dto.description;
        product.price = dto.price;
        product.quantity = dto.quantity;
        return product;
    }

    public static ProductResponse toResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.id = product.id;
        dto.name = product.name;
        dto.description = product.description;
        dto.price = product.price;
        dto.quantity = product.quantity;
        return dto;
    }
}
