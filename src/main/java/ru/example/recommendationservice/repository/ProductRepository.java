package ru.example.recommendationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.example.recommendationservice.document.Product;

public interface ProductRepository extends MongoRepository<Product, Long> {
}
