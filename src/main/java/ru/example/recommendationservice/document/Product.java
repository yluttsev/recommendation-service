package ru.example.recommendationservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * Документ продукта
 */
@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Product {

    @Id
    private Long id;

    @Field(name = "name")
    private String name;

    @Field(name = "price")
    private BigDecimal price;

    @Field(name = "category")
    private Long category;

    @Field(name = "views")
    private Integer views;
}
