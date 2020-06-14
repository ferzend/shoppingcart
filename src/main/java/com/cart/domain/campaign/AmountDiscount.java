package com.cart.domain.campaign;

import java.math.BigDecimal;

public class AmountDiscount extends Discount {
    public AmountDiscount(int categoryId, Double discount, int minNumberOfProduct) {
        super(categoryId, discount, minNumberOfProduct);
    }

    public BigDecimal getApplicableDiscount(int numberOfProduct, BigDecimal amount) {
        if (isApplicable(numberOfProduct)) {
            return BigDecimal.valueOf(getDiscount());
        }
        return BigDecimal.ZERO;
    }
}
