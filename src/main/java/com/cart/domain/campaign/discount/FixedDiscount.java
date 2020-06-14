package com.cart.domain.campaign.discount;

import java.math.BigDecimal;

public class FixedDiscount extends Discount {
    public FixedDiscount(int categoryId, BigDecimal discount, int minNumberOfProduct) {
        super(categoryId, discount, minNumberOfProduct);
    }

    public BigDecimal getApplicableDiscount(int numberOfProduct, BigDecimal amount) {
        if (isApplicable(numberOfProduct)) {
            return getDiscount();
        }
        return BigDecimal.ZERO;
    }
}
