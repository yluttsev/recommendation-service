package ru.example.recommendationservice.dto;

import java.math.BigDecimal;

/**
 * DTO продукта
 *
 * @param id       ID продукта
 * @param name     Название продукта
 * @param price    Цена продукта
 * @param category ID категории продукта
 * @param views    Количество просмотров продукта
 */
public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Long category,
        Integer views
) {
}
