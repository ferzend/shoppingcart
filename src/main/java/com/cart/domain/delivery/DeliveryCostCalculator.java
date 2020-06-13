package com.cart.domain.delivery;

import com.cart.domain.ShoppingCart;
import com.cart.infrastructure.delivery.DeliveryService;

import java.math.BigDecimal;

public class DeliveryCostCalculator {
    private DeliveryService deliveryService;
    private DeliveryCost deliveryCost;

    public DeliveryCostCalculator(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public BigDecimal calculate(ShoppingCart shoppingCart) {
        deliveryCost = deliveryService.getDeliveryCost();

        if (shoppingCart.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return getCategoryCost(shoppingCart).add(getProductCost(shoppingCart)).add(deliveryCost.getFixedCost());
    }

    private BigDecimal getCategoryCost(ShoppingCart shoppingCart) {
        return deliveryCost.getCostPerDelivery().multiply(BigDecimal.valueOf(shoppingCart.getNumberOfCategory()));
    }

    private BigDecimal getProductCost(ShoppingCart shoppingCart) {
        return deliveryCost.getCostPerProdcut().multiply(BigDecimal.valueOf(shoppingCart.getNumberOfProduct()));
    }
}
