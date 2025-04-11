package ru.example.recommendationservice.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.example.recommendationservice.document.Product;

import java.util.List;

/**
 * Репозиторий для {@link Product}
 */
public interface ProductRepository extends MongoRepository<Product, Long> {

    /**
     * Получение продуктов по категории с сортировкой количества просмотров от большего к меньшему
     *
     * @param categoryId ID категории продуктов
     * @return список продуктов
     */
    List<Product> findByCategoryOrderByViewsDesc(Long categoryId);

    /**
     * Получение продуктов с сортировкой количества просмотров от большего к меньшему
     *
     * @return список продуктов
     */
    List<Product> findAllByOrderByViewsDesc();

    /**
     * Инкремент количества просмотров продукта
     *
     * @param productId ID продукта
     */
    @Aggregation(pipeline = {
            "{ $match: { _id: ?0 } }",
            "{ $set: { views: { $add: ['$views', 1] } } }",
            "{ $merge: { into: 'products', whenMatched: 'replace' } }"
    })
    void updateProductViews(Long productId);
}
