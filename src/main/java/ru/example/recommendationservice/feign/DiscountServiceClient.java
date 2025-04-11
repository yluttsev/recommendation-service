package ru.example.recommendationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "discount-service-client", url = "${discount.service.url}")
public interface DiscountServiceClient {

    @GetMapping("/discount/fixed")
    BigDecimal calculateFixedDiscount(@RequestParam("price") BigDecimal price,
                                      @RequestParam("product_category") Long productCategoryId,
                                      @RequestParam("client_category") Long clientCategoryId);

    @GetMapping("/discount/variable")
    BigDecimal calculateVariableDiscount(@RequestParam("price") BigDecimal price,
                                         @RequestParam("product_category") Long productCategoryId,
                                         @RequestParam("client_category") Long clientCategoryId);

    @GetMapping("/discount/loyalty")
    BigDecimal calculateLoyaltyDiscount(@RequestParam("price") BigDecimal price,
                                        @RequestParam("user_id") Long userId);
}
