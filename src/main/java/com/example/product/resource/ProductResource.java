package com.example.product.resource;

import com.example.product.dto.ApiResponse;
import com.example.product.dto.ProductRequest;
import com.example.product.dto.ProductResponse;
import com.example.product.service.ProductService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService service;

    @POST
    public Uni<ApiResponse<ProductResponse>>  create(ProductRequest request) {
        return service.create(request);
    }

    @GET
    public Uni<List<ProductResponse>> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Uni<ProductResponse> getById(@PathParam("id") Long id) {
        return service.getById(id);
    }

    @PUT
    @Path("/{id}")
    public Uni<ApiResponse<ProductResponse>>  update(
            @PathParam("id") Long id,
            ProductRequest request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Uni<ApiResponse<Void>> delete(@PathParam("id") Long id) {
        return service.delete(id);
    }

    @GET
    @Path("/{id}/stock")
    public Uni<Boolean> checkStock(
            @PathParam("id") Long id,
            @QueryParam("count") Integer count) {
        return service.checkStock(id, count);
    }

    @GET
    @Path("/sorted/price")
    public Uni<List<ProductResponse>> sortedByPrice() {
        return service.getSortedByPrice();
    }
}
