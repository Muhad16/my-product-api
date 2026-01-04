package com.example.product.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof ProductNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(
                            404,
                            "Product Not Found",
                            ex.getMessage()))
                    .build();
        }

        if (exception instanceof IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(
                            400,
                            "Bad Request",
                            ex.getMessage()))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(
                        500,
                        "Internal Server Error",
                        "Something went wrong"))
                .build();
    }
}
