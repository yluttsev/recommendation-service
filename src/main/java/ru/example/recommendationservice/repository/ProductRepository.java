package ru.example.recommendationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.example.recommendationservice.document.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Long> {
    List<Product> findByCategoryIdOrderByViewsDesc(Long categoryId);
}
