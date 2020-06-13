package com.cart.domain.campaign;

import java.math.BigDecimal;

public class AmountCoupon extends Coupon {
    public AmountCoupon(Double minPurchaseAmount, Double discount) {
        super(minPurchaseAmount, discount);
    }

    public BigDecimal getApplicableDiscount(BigDecimal totalPriceOfProducts) {
        if (isApplicable(totalPriceOfProducts)) {
            return BigDecimal.valueOf(getDiscount());
        }
        return BigDecimal.ZERO;
    }
}
