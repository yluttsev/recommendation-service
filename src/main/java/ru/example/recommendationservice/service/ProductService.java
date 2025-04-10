package ru.example.recommendationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.mapper.ProductMapper;
import ru.example.recommendationservice.dto.ProductDto;
import ru.example.recommendationservice.exception.ProductNotFoundException;
import ru.example.recommendationservice.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllById(List<Long> ids) {
        return ids.stream()
                .map(id -> productRepository.findById(id).orElseThrow(
                        () -> new ProductNotFoundException(id)
                ))
                .map(productMapper::mapDocumentToDto)
                .toList();
    }

    public List<ProductDto> getTopProductsByCategories(List<Long> categoryIds, int limit) {
        return categoryIds.stream()
                .flatMap(categoryId -> productRepository.findByCategoryIdOrderByViewsDesc(categoryId).stream())
                .sorted((p1, p2) -> Long.compare(p2.getViews(), p1.getViews()))
                .limit(limit)
                .map(productMapper::mapDocumentToDto)
                .toList();
    }

}
