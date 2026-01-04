package com.example.product.service;

import com.example.product.dto.ApiResponse;
import com.example.product.dto.ProductRequest;
import com.example.product.dto.ProductResponse;
import com.example.product.entity.Product;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.dto.ProductMapper;
import com.example.product.repository.ProductRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository repository;

    @WithTransaction
    public Uni<ApiResponse<ProductResponse>> create(ProductRequest request) {

        if (request.price == null || request.price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }

        if (request.quantity == null || request.quantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Product product = ProductMapper.toEntity(request);

        return repository.persist(product)
                .map(saved -> ApiResponse.success(
                        ProductMapper.toResponse(saved),
                        "Product created successfully"
                ));
    }



    @WithSession
    public Uni<List<ProductResponse>> getAll() {
        return repository.listAll()
                .map(list -> list.stream()
                        .map(ProductMapper::toResponse)
                        .toList());
    }

    @WithSession
    public Uni<ProductResponse> getById(Long id) {
        return repository.findById(id)
                .onItem().ifNull().failWith(new ProductNotFoundException(id))
                .map(ProductMapper::toResponse);
    }

    @WithTransaction
    public Uni<ApiResponse<ProductResponse>> update(Long id, ProductRequest request) {

        return repository.findById(id)
                .onItem().ifNull().failWith(new ProductNotFoundException(id))
                .onItem().invoke(p -> {
                    p.name = request.name;
                    p.description = request.description;
                    p.price = request.price;
                    p.quantity = request.quantity;
                })
                .map(updated -> ApiResponse.success(
                        ProductMapper.toResponse(updated),
                        "Product updated successfully"
                ));
    }



    @WithTransaction
    public Uni<ApiResponse<Void>> delete(Long id) {

        return repository.deleteById(id)
                .onItem().transform(deleted -> {
                    if (!deleted) {
                        throw new ProductNotFoundException(id);
                    }
                    return ApiResponse.success(null, "Product deleted successfully");
                });
    }



    @WithSession
    public Uni<Boolean> checkStock(Long id, Integer count) {

        if (count == null || count < 0) {
            throw new IllegalArgumentException("Count must be positive");
        }

        return repository.findById(id)
                .onItem().ifNull().failWith(new ProductNotFoundException(id))
                .map(product -> product.quantity >= count);
    }

    @WithSession
    public Uni<List<ProductResponse>> getSortedByPrice() {
        return repository.find("ORDER BY price ASC").list()
                .map(list -> list.stream()
                        .map(ProductMapper::toResponse)
                        .toList());
    }
}
