package com.cart.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartSummary {
    private List<CartItem> cartItems;
    private BigDecimal campaignDiscount;
    private BigDecimal couponDiscount;
    private BigDecimal deliveryAmount;
}
