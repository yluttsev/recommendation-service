package ru.example.recommendationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.example.recommendationservice.dto.ProductDto;
import ru.example.recommendationservice.dto.ProductWithDiscountDto;
import ru.example.recommendationservice.service.ProductService;
import ru.example.recommendationservice.service.RecommendationService;

import java.util.List;

/**
 * Контроллер для работы с рекомендациями
 */
@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final ProductService productService;

    /**
     * Получение списка рекомендованных продуктов для пользователя
     *
     * @param userId ID пользователя
     * @return список {@link ProductDto DTO} продуктов
     */
    @GetMapping("/{userId}")
    public List<ProductDto> getRecommendations(
            @PathVariable Long userId) {
        return recommendationService.getPersonalizedRecommendationsForUserById(userId);
    }

    /**
     * Получение продуктов с максимальной скидкой
     *
     * @param userId ID пользователя
     * @return список {@link ProductWithDiscountDto DTO} продуктов со скидкой
     */
    @GetMapping("/hot")
    public List<ProductWithDiscountDto> getProductsWithMaxDiscounts(@RequestParam("user_id") Long userId) {
        return productService.getProductsWithMaxDiscount(userId);
    }

    /**
     * Получение продуктов с большим количеством просмотров
     *
     * @return список {@link ProductDto DTO} продуктов
     */
    @GetMapping("/popular")
    public List<ProductDto> getPopularProducts() {
        return productService.getPopularProducts();
    }
}
