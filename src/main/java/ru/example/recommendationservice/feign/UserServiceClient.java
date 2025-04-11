package ru.example.recommendationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.recommendationservice.dto.PurchaseDto;
import ru.example.recommendationservice.dto.UserCategoryDto;

import java.util.List;

/**
 * Feign клиент для работы с User Service
 */
@FeignClient(name = "user-service-client", url = "${user.service.url}")
public interface UserServiceClient {

    /**
     * Получение истории покупок пользователя
     *
     * @param userId     ID пользователя
     * @param pageNumber Номер страницы
     * @param pageSize   Количество элементов на странице
     * @return Список {@link PurchaseDto DTO} покупок
     */
    @GetMapping("/users/{userId}/purchases")
    List<PurchaseDto> getPurchaseHistoryByUserId(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", defaultValue = "10") int pageSize);

    /**
     * Получение категории пользователя
     *
     * @param userId ID пользователя
     * @return {@link UserCategoryDto DTO} категории пользователя
     */
    @GetMapping("/users/{userId}")
    UserCategoryDto getUserCategoryById(@PathVariable("userId") Long userId);
}
