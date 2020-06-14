package com.cart.domain.campaign;

import java.math.BigDecimal;

public class PercentageCoupon extends Coupon {
    public PercentageCoupon(Double minPurchaseAmount, Double discount) {
        super(minPurchaseAmount, discount);
    }

    public BigDecimal getApplicableDiscount(BigDecimal amount) {
        if (isApplicable(amount)) {
             return amount.multiply(BigDecimal.valueOf(getDiscount())).divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}