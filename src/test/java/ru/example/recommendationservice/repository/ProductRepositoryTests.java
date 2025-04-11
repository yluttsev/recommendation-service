package ru.example.recommendationservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.example.recommendationservice.document.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@Testcontainers
class ProductRepositoryTests {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer();

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void addTestProducts() {
        productRepository.deleteAll();
        productRepository.saveAll(List.of(
                new Product(1L, "Product 1", BigDecimal.valueOf(18000), 1L, 400),
                new Product(2L, "Product 2", BigDecimal.valueOf(2000), 2L, 800),
                new Product(3L, "Product 3", BigDecimal.valueOf(250), 3L, 1000),
                new Product(4L, "Product 4", BigDecimal.valueOf(300), 4L, 100)
        ));
    }

    @Test
    void findPopularProducts_shouldReturnPopularProductsWithDescOrdering() {
        List<Product> products = productRepository.findAllByOrderByViewsDesc();
        for (int i = 0; i < products.size() - 1; i++) {
            assertTrue(products.get(i).getViews().compareTo(products.get(i + 1).getViews()) >= 0);
        }
    }
}
