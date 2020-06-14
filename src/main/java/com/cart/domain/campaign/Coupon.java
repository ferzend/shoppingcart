package com.cart.domain.campaign;

import java.math.BigDecimal;

public abstract class Coupon {
    private Double minPurchaseAmount;
    private Double discount;

    public Coupon(Double minPurchaseAmount, Double discount) {
        this.minPurchaseAmount = minPurchaseAmount;
        this.discount = discount;
    }

    boolean isApplicable(BigDecimal amount) {
        return BigDecimal.valueOf(getMinPurchaseAmount()).compareTo(amount) <= 0;
    }

    public abstract BigDecimal getApplicableDiscount(BigDecimal amount);

    public Double getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public Double getDiscount() {
        return discount;
    }
}
