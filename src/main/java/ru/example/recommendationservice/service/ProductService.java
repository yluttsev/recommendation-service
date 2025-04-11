package ru.example.recommendationservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.recommendationservice.document.Product;
import ru.example.recommendationservice.dto.ProductDto;
import ru.example.recommendationservice.dto.ProductWithDiscountDto;
import ru.example.recommendationservice.exception.ProductNotFoundException;
import ru.example.recommendationservice.feign.DiscountServiceClient;
import ru.example.recommendationservice.feign.UserServiceClient;
import ru.example.recommendationservice.mapper.ProductMapper;
import ru.example.recommendationservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Сервис для работы с {@link Product}
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DiscountServiceClient discountServiceClient;
    private final UserServiceClient userServiceClient;

    /**
     * Получение продуктов по списку ID
     *
     * @param ids список ID продуктов
     * @return список {@link ProductDto DTO} продуктов
     */
    public List<ProductDto> getAllById(List<Long> ids) {
        ids.forEach(productRepository::updateProductViews);
        return ids.stream()
                .map(id -> productRepository.findById(id).orElseThrow(
                        () -> new ProductNotFoundException(id)
                ))
                .map(productMapper::mapDocumentToDto)
                .toList();
    }

    /**
     * Получение топ продуктов из категорий продуктов
     *
     * @param categoryIds Список ID категорий продуктов
     * @param limit       Количество топа продуктов
     * @return список {@link ProductDto DTO} продуктов
     */
    public List<ProductDto> getTopProductsByCategories(List<Long> categoryIds, int limit) {
        return categoryIds.stream()
                .flatMap(categoryId -> productRepository.findByCategoryOrderByViewsDesc(categoryId).stream())
                .sorted((p1, p2) -> Long.compare(p2.getViews(), p1.getViews()))
                .limit(limit)
                .map(productMapper::mapDocumentToDto)
                .toList();
    }

    /**
     * Получение продуктов с сортировкой просмотров от большего к меньшему
     *
     * @return список {@link ProductDto DTO} продуктов
     */
    public List<ProductDto> getPopularProducts() {
        return productRepository.findAllByOrderByViewsDesc()
                .stream()
                .map(productMapper::mapDocumentToDto)
                .toList();
    }

    /**
     * Получение продуктов с максимальными скидками.<br>
     * Метод обращается к Discount Service и считает скидку всех типов, затем сортирует от большего к меньшему
     *
     * @param userId ID пользователя
     * @return список {@link ProductWithDiscountDto DTO} продуктов с максимальными скидками
     */
    public List<ProductWithDiscountDto> getProductsWithMaxDiscount(Long userId) {
        Long userCategory = userServiceClient.getUserCategoryById(userId).category();
        List<Product> products = productRepository.findAll();
        List<ProductWithDiscountDto> productsWithDiscount = new ArrayList<>();
        products.forEach(product -> {
            BigDecimal priceWithDiscount = discountServiceClient.calculateFixedDiscount(product.getPrice(), product.getCategory(), userCategory);
            if (priceWithDiscount.compareTo(product.getPrice()) == 0) {
                priceWithDiscount = discountServiceClient.calculateVariableDiscount(product.getPrice(), product.getCategory(), userCategory);
                if (priceWithDiscount.compareTo(product.getPrice()) == 0) {
                    priceWithDiscount = discountServiceClient.calculateLoyaltyDiscount(product.getPrice(), userId);
                }
            }
            productsWithDiscount.add(new ProductWithDiscountDto(
                    product.getName(),
                    product.getPrice(),
                    priceWithDiscount
            ));
        });
        productsWithDiscount.sort(Comparator.comparing(p -> (p.price().subtract(p.priceWithDiscount())), Comparator.reverseOrder()));
        return productsWithDiscount;
    }

    /**
     * Добавление тестовых продуктов в MongoDB
     */
    @PostConstruct
    public void initTestProducts() {
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                    new Product(1L, "Product 1", BigDecimal.valueOf(18000), 1L, 0),
                    new Product(2L, "Product 2", BigDecimal.valueOf(2000), 2L, 0),
                    new Product(3L, "Product 3", BigDecimal.valueOf(250), 3L, 0),
                    new Product(4L, "Product 4", BigDecimal.valueOf(300), 4L, 0),
                    new Product(5L, "Product 5", BigDecimal.valueOf(3500), 1L, 0),
                    new Product(6L, "Product 6", BigDecimal.valueOf(150), 2L, 0),
                    new Product(7L, "Product 7", BigDecimal.valueOf(750), 3L, 0),
                    new Product(8L, "Product 8", BigDecimal.valueOf(5000), 4L, 0)
            ));
        }
    }
}
