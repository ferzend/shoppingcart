package com.cart.domain.delivery;

import com.cart.domain.ShoppingCart;

import java.math.BigDecimal;

public class DeliveryCostCalculator {
    private DeliveryService deliveryService;
    private DeliveryCost deliveryCost;

    public DeliveryCostCalculator(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public BigDecimal calculate(ShoppingCart shoppingCart) {
        if (shoppingCart.isEmpty()) {
            return BigDecimal.ZERO;
        }

        deliveryCost = deliveryService.getDeliveryCost();

        return getCategoryCost(shoppingCart).add(getProductCost(shoppingCart)).add(deliveryCost.getFixedCost());
    }

    private BigDecimal getCategoryCost(ShoppingCart shoppingCart) {
        return deliveryCost.getCostPerDelivery().multiply(BigDecimal.valueOf(shoppingCart.getNumberOfCategory()));
    }

    private BigDecimal getProductCost(ShoppingCart shoppingCart) {
        return deliveryCost.getCostPerProduct().multiply(BigDecimal.valueOf(shoppingCart.getNumberOfProduct()));
    }
}
