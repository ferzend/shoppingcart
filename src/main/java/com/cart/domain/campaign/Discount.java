package com.cart.domain.campaign;

import java.math.BigDecimal;

public abstract class Discount {
    private int categoryId;
    private Double discount;
    private int minNumberOfProduct;

    public Discount(int categoryId, Double discount, int minNumberOfProduct) {
        this.categoryId = categoryId;
        this.discount = discount;
        this.minNumberOfProduct = minNumberOfProduct;
    }

    public abstract BigDecimal getApplicableDiscount(int numberOfProduct, BigDecimal totalPriceOfProducts);

    boolean isApplicable(int numberOfProduct) {
        return numberOfProduct >= getMinNumberOfProduct();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Double getDiscount() {
        return discount;
    }

    public int getMinNumberOfProduct() {
        return minNumberOfProduct;
    }
}
