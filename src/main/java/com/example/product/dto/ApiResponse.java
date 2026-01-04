package com.example.product.dto;


public class ApiResponse<T> {

    public T data;
    public String message;

    public ApiResponse() {}

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }
}

