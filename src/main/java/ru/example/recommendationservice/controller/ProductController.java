package ru.example.recommendationservice.controller;

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
public class ProductController {

    private final ProductService productService;

    /**
     * Получение списка продуктов
     *
     * @param ids Список ID продуктов
     * @return список продуктов
     */
    @GetMapping
    public List<ProductDto> getById(@RequestParam("id") List<Long> ids) {
        return productService.getAllById(ids);
    }
}
