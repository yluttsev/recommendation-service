package ru.example.recommendationservice.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO покупки
 *
 * @param id           ID покупки
 * @param amount       Общая сумма покупки
 * @param purchaseDate Дата покупки
 * @param products     Список {@link ProductDto DTO} продуктов
 */
public record PurchaseDto(
        Long id,
        BigDecimal amount,
        long purchaseDate,
        List<ProductDto> products
) {
}
