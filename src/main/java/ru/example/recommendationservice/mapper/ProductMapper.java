package ru.example.recommendationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.example.recommendationservice.document.Product;
import ru.example.recommendationservice.dto.ProductDto;

/**
 * Маппер для {@link Product}
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDto mapDocumentToDto(Product product);
}
