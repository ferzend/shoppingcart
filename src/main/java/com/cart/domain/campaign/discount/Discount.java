package com.cart.domain.campaign.discount;

import java.math.BigDecimal;

public abstract class Discount {
    private int categoryId;
    private BigDecimal discount;
    private int minNumberOfProduct;

    public Discount(int categoryId, BigDecimal discount, int minNumberOfProduct) {
        this.categoryId = categoryId;
        this.discount = discount;
        this.minNumberOfProduct = minNumberOfProduct;
    }

    public abstract BigDecimal getApplicableDiscount(int numberOfProduct, BigDecimal amount);

    boolean isApplicable(int numberOfProduct) {
        return numberOfProduct >= getMinNumberOfProduct();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public int getMinNumberOfProduct() {
        return minNumberOfProduct;
    }
}
