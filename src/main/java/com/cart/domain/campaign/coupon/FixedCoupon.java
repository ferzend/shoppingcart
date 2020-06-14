package com.cart.domain.campaign.coupon;

import java.math.BigDecimal;

public class FixedCoupon extends Coupon {
    public FixedCoupon(BigDecimal minPurchaseAmount, BigDecimal discount) {
        super(minPurchaseAmount, discount);
    }

    public BigDecimal getApplicableDiscount(BigDecimal amount) {
        if (isApplicable(amount)) {
            return getDiscount();
        }
        return BigDecimal.ZERO;
    }
}
