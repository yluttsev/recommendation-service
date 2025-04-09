package ru.example.recommendationservice.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Long category,
        Integer views
) {
}
