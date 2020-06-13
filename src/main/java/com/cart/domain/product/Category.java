package com.cart.domain.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public class Category {
    private Integer categoryId;
    @Setter
    private Integer parentCategoryId;
    private String title;

    public Category(Integer categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }
}
