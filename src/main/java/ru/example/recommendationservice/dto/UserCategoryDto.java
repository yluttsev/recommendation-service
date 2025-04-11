package ru.example.recommendationservice.dto;

/**
 * DTO категории пользователя
 *
 * @param category ID категории пользователя
 */
public record UserCategoryDto(
        Long category
) {
}
