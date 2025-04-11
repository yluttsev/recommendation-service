package ru.example.recommendationservice.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "API рекомендаций", description = "API рекомендованных продуктов")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final ProductService productService;

    /**
     * Получение списка рекомендованных продуктов для пользователя
     *
     * @param userId ID пользователя
     * @return список {@link ProductDto DTO} продуктов
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка рекомендованных продуктов",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Пользователь с данным ID не найден"
            ),
            @ApiResponse(
                    responseCode = "500", description = "Внутренняя ошибка сервера"
            )
    })
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
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка продуктов с максимальными скидками",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Пользователь с данным ID не найден"
            ),
            @ApiResponse(
                    responseCode = "500", description = "Внутренняя ошибка сервера"
            )
    })
    @GetMapping("/hot")
    public List<ProductWithDiscountDto> getProductsWithMaxDiscounts(@RequestParam("user_id") Long userId) {
        return productService.getProductsWithMaxDiscount(userId);
    }

    /**
     * Получение продуктов с большим количеством просмотров
     *
     * @return список {@link ProductDto DTO} продуктов
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка популярных продуктов",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "500", description = "Внутренняя ошибка сервера"
            )
    })
    @GetMapping("/popular")
    public List<ProductDto> getPopularProducts() {
        return productService.getPopularProducts();
    }
}
