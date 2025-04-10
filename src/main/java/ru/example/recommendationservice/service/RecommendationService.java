package ru.example.recommendationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.recommendationservice.dto.ProductDto;
import ru.example.recommendationservice.dto.PurchaseDto;
import ru.example.recommendationservice.feign.PurchaseHistoryClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final PurchaseHistoryClient purchaseHistoryClient;
    private final ProductService productService;

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int TOP_LIMIT = 3;

    public List<ProductDto> getPersonalizedRecommendationsForUserById(Long userId) {
        // История покупок
        List<PurchaseDto> clientPurchaseHistory = purchaseHistoryClient.getPurchaseHistoryByUserId(
                userId,
                0,
                DEFAULT_PAGE_SIZE
        );
        // Извлекаем уникальные купленные в HashSet
        Set<ProductDto> purchaseProducts = clientPurchaseHistory.stream()
                .flatMap(purchaseDto -> purchaseDto.products().stream())
                .collect(Collectors.toCollection(HashSet::new));

        // Находим ТОП-3 категории продуктов у пользователя
        List<Long> topCategories = findTopCategories(purchaseProducts);

        // По ТОП-3 товара из каждой категории
        List<ProductDto> recommendedProducts = productService.getTopProductsByCategories(topCategories, TOP_LIMIT);

        // Фильтруем уже купленные товары при возврате
        return recommendedProducts.stream()
                .filter(p -> !purchaseProducts.contains(p))
                .toList();
    }

    private List<Long> findTopCategories(Collection<ProductDto> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        ProductDto::category,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(TOP_LIMIT)
                .map(Map.Entry::getKey)
                .toList();
    }

}
