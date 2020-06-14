package com.cart.domain.campaign;

import java.math.BigDecimal;

public class RateDiscount extends Discount {
    public RateDiscount(int categoryId, Double discount, int minNumberOfProduct) {
        super(categoryId, discount, minNumberOfProduct);
    }

    public BigDecimal getApplicableDiscount(int numberOfProduct, BigDecimal amount) {
        if (isApplicable(numberOfProduct)) {
            return amount.multiply(BigDecimal.valueOf(getDiscount())).divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
