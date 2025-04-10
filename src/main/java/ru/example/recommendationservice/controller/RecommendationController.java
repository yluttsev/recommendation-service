package ru.example.recommendationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.recommendationservice.dto.ProductDto;
import ru.example.recommendationservice.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<ProductDto> getRecommendations(
            @PathVariable Long userId) {
        return recommendationService.getPersonalizedRecommendationsForUserById(userId);
    }

/*
    TODO
    @GetMapping("/hot")
    public List<ProductDto> getPopularProducts(
            @RequestParam(required = false, defaultValue = "10") int limit) {
        return recommendationService.getPopularProductsByDiscount(limit);
   }
*/

}
