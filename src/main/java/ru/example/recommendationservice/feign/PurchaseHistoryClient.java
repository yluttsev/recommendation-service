package ru.example.recommendationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.recommendationservice.dto.PurchaseDto;

import java.util.List;

@FeignClient(name = "user-service-client", url = "${user.service.url}")
public interface PurchaseHistoryClient {

    @GetMapping("/users/{userId}/purchases")
    List<PurchaseDto> getPurchaseHistoryByUserId(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", defaultValue = "10") int pageSize);

}
