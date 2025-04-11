package ru.example.recommendationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductWithDiscountDto(
        String name,
        BigDecimal price,

        @JsonProperty("price_with_discount")
        BigDecimal priceWithDiscount
) {
}
