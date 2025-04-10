package ru.example.recommendationservice.dto;

import java.math.BigDecimal;
import java.util.List;

public record PurchaseDto(
        Long id,
        BigDecimal amount,
        long purchaseDate,
        List<ProductDto> products
) {}
