package ru.example.recommendationservice.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.example.recommendationservice.document.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Long> {

    List<Product> findByCategoryOrderByViewsDesc(Long categoryId);

    List<Product> findAllByOrderByViewsDesc();

    @Aggregation(pipeline = {
            "{ $match: { _id: ?0 } }",
            "{ $set: { views: { $add: ['$views', 1] } } }",
            "{ $merge: { into: 'products', whenMatched: 'replace' } }"
    })
    void updateProductViews(Long productId);
}
