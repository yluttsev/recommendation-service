package ru.example.recommendationservice.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Product with id '%d' not found".formatted(productId));
    }
}
