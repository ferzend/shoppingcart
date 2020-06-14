package com.cart.domain.campaign.coupon;

import javafx.scene.transform.Scale;

import java.math.BigDecimal;

public class PercentageCoupon extends Coupon {
    public PercentageCoupon(BigDecimal minPurchaseAmount, BigDecimal discount) {
        super(minPurchaseAmount, discount);
    }

    public BigDecimal getApplicableDiscount(BigDecimal amount) {
        if (isApplicable(amount)) {
             return amount.multiply(getDiscount()).divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
