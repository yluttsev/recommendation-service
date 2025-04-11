package ru.example.recommendationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * Feign клиент для общения с Discount Service
 */
@FeignClient(name = "discount-service-client", url = "${discount.service.url}")
public interface DiscountServiceClient {

    /**
     * Расчет фиксированной скидки
     *
     * @param price             Сумма покупки
     * @param productCategoryId ID категории продукта
     * @param clientCategoryId  ID категории клиента
     * @return сумма покупки с учетом скидок
     */
    @GetMapping("/discount/fixed")
    BigDecimal calculateFixedDiscount(@RequestParam("price") BigDecimal price,
                                      @RequestParam("product_category") Long productCategoryId,
                                      @RequestParam("client_category") Long clientCategoryId);

    /**
     * Расчет переменной скидки
     *
     * @param price             Сумма покупки
     * @param productCategoryId ID категории продукта
     * @param clientCategoryId  ID категории клиента
     * @return сумма покупки с учетом скидок
     */
    @GetMapping("/discount/variable")
    BigDecimal calculateVariableDiscount(@RequestParam("price") BigDecimal price,
                                         @RequestParam("product_category") Long productCategoryId,
                                         @RequestParam("client_category") Long clientCategoryId);

    /**
     * Расчет накопительной скидки
     *
     * @param price  Сумма покупки
     * @param userId ID пользователя
     * @return сумма покупки с учетом скидок
     */
    @GetMapping("/discount/loyalty")
    BigDecimal calculateLoyaltyDiscount(@RequestParam("price") BigDecimal price,
                                        @RequestParam("user_id") Long userId);
}
