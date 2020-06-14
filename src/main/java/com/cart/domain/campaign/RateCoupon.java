package com.cart.domain.campaign;

import java.math.BigDecimal;

public class RateCoupon extends Coupon {
    public RateCoupon(Double minPurchaseAmount, Double discount) {
        super(minPurchaseAmount, discount);
    }

    public BigDecimal getApplicableDiscount(BigDecimal totalPriceOfProducts) {
        if (isApplicable(totalPriceOfProducts)) {
             return totalPriceOfProducts.multiply(BigDecimal.valueOf(getDiscount())).divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
