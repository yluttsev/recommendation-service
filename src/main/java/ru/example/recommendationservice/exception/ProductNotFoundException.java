package ru.example.recommendationservice.exception;

/**
 * Исключение для ненайденного продукта
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Product with id '%d' not found".formatted(productId));
    }
}
