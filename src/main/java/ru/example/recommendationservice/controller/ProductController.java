package ru.example.recommendationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.example.recommendationservice.document.Product;
import ru.example.recommendationservice.dto.ProductDto;
import ru.example.recommendationservice.service.ProductService;

import java.util.List;

/**
 * Контроллер для работы с {@link Product}
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "API продуктов", description = "API для работы с продуктами")
public class ProductController {

    private final ProductService productService;

    /**
     * Получение списка продуктов
     *
     * @param ids Список ID продуктов
     * @return список продуктов
     */
    @Operation(summary = "Получение списка продуктов", description = "Получение списка продуктов по переданным ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка продуктов",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Неверный ID продукта"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Продукт с данным ID не найден"
            ),
            @ApiResponse(
                    responseCode = "500", description = "Внутрення ошибка сервера"
            )
    })
    @GetMapping
    public List<ProductDto> getById(@RequestParam("id") List<Long> ids) {
        return productService.getAllById(ids);
    }
}
