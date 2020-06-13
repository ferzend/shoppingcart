package com.cart.domain.product;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class Product {
    private String title;
    private BigDecimal price;
    private Category category;

    public Product(String title, BigDecimal price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }
}
