package com.cart.application;

import com.cart.domain.CartSummary;
import com.cart.domain.ShoppingCart;
import com.cart.domain.campaign.Coupon;
import com.cart.domain.delivery.DeliveryCostCalculator;
import com.cart.infrastructure.campaign.CampaignService;
import com.cart.infrastructure.printer.Printer;

import java.math.BigDecimal;
import java.util.Optional;

public class ShoppingCartService {
    private DeliveryCostCalculator deliveryCostCalculator;
    private CampaignService campaignService;
    private Printer printer;

    public ShoppingCartService(DeliveryCostCalculator deliveryCostCalculator, CampaignService campaignService, Printer printer) {
        this.deliveryCostCalculator = deliveryCostCalculator;
        this.campaignService = campaignService;
        this.printer = printer;
    }

    public CartSummary calculateSummary(ShoppingCart shoppingCart) {
        BigDecimal delivery = getDelivery(shoppingCart);
        applyDiscounts(shoppingCart);
        applyCoupon(shoppingCart);
        CartSummary cartSummary = createCartSummary(shoppingCart, delivery);
        printer.print(cartSummary);

        return cartSummary;
    }

    private void applyDiscounts(ShoppingCart shoppingCart) {
        shoppingCart.applyDiscounts(campaignService.getCapaignsByCategories(shoppingCart.getCategories()));
    }

    private void applyCoupon(ShoppingCart shoppingCart) {
        Optional<Coupon> coupon = campaignService.getCoupon();
        coupon.ifPresent(shoppingCart::applyCoupon);
    }

    private CartSummary createCartSummary(ShoppingCart shoppingCart, BigDecimal delivery) {
        return CartSummary.builder()
                .cartItems(shoppingCart.getProducts())
                .campaignDiscount(shoppingCart.getCampaignDiscount())
                .couponDiscount(shoppingCart.getCouponDiscount())
                .deliveryAmount(delivery)
                .build();
    }

    private BigDecimal getDelivery(ShoppingCart shoppingCart) {
        return deliveryCostCalculator.calculate(shoppingCart);
    }
}
