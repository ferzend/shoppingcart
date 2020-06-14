package com.cart.domain.campaign.discount;

import java.math.BigDecimal;

public class PercentageDiscount extends Discount {
    public PercentageDiscount(int categoryId, BigDecimal discount, int minNumberOfProduct) {
        super(categoryId, discount, minNumberOfProduct);
    }

    public BigDecimal getApplicableDiscount(int numberOfProduct, BigDecimal amount) {
        if (isApplicable(numberOfProduct)) {
            return amount.multiply(getDiscount()).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
