package com.example.product.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ProductNotFoundException extends WebApplicationException {

    public ProductNotFoundException(Long id) {
        super("Product with id " + id + " not found", Response.Status.NOT_FOUND);
    }
}
