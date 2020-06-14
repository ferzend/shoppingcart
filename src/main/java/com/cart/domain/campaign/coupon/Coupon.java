package com.cart.domain.campaign.coupon;

import java.math.BigDecimal;

public abstract class Coupon {
    private BigDecimal minPurchaseAmount;
    private BigDecimal discount;

    public Coupon(BigDecimal minPurchaseAmount, BigDecimal discount) {
        this.minPurchaseAmount = minPurchaseAmount;
        this.discount = discount;
    }

    boolean isApplicable(BigDecimal amount) {
        return getMinPurchaseAmount().compareTo(amount) <= 0;
    }

    public abstract BigDecimal getApplicableDiscount(BigDecimal amount);

    public BigDecimal getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
