package ru.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.example.recommendationservice.document.Product;
import ru.example.recommendationservice.dto.ProductDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDto mapDocumentToDto(Product product);
}
