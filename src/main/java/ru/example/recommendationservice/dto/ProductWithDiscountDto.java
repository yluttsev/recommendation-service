package ru.example.recommendationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * DTO продукта со скидкой
 *
 * @param name              Название продукта
 * @param price             Исходная цена продукта
 * @param priceWithDiscount Цена продукта с учетом скидок
 */
public record ProductWithDiscountDto(
        String name,
        BigDecimal price,

        @JsonProperty("price_with_discount")
        BigDecimal priceWithDiscount
) {
}
