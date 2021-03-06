package com.cart.application;

import com.cart.domain.CartSummary;
import com.cart.domain.ShoppingCart;
import com.cart.domain.campaign.coupon.Coupon;
import com.cart.domain.campaign.coupon.CouponService;
import com.cart.domain.delivery.DeliveryCostCalculator;
import com.cart.domain.campaign.discount.DiscountService;
import com.cart.domain.printer.CartPrinter;

import java.math.BigDecimal;
import java.util.Optional;

public class ShoppingCartService {
    private DeliveryCostCalculator deliveryCostCalculator;
    private DiscountService discountService;
    private CouponService couponService;
    private CartPrinter cartPrinter;

    public ShoppingCartService(DeliveryCostCalculator deliveryCostCalculator,
                               DiscountService discountService,
                               CouponService couponService,
                               CartPrinter cartPrinter) {
        this.deliveryCostCalculator = deliveryCostCalculator;
        this.discountService = discountService;
        this.couponService = couponService;
        this.cartPrinter = cartPrinter;
    }

    public CartSummary calculateSummary(ShoppingCart shoppingCart) {
        BigDecimal delivery = getDelivery(shoppingCart);
        applyDiscounts(shoppingCart);
        applyCoupon(shoppingCart);
        CartSummary cartSummary = createCartSummary(shoppingCart, delivery);
        cartPrinter.print(cartSummary);

        return cartSummary;
    }

    private void applyDiscounts(ShoppingCart shoppingCart) {
        shoppingCart.applyDiscounts(discountService.getDiscountsByCategories(shoppingCart.getCategories()));
    }

    private void applyCoupon(ShoppingCart shoppingCart) {
        Optional<Coupon> coupon = couponService.getCoupon();
        coupon.ifPresent(shoppingCart::applyCoupon);
    }

    private CartSummary createCartSummary(ShoppingCart shoppingCart, BigDecimal delivery) {
        return CartSummary.builder()
                .cartItems(shoppingCart.getCartItems())
                .campaignDiscount(shoppingCart.getCampaignDiscount())
                .couponDiscount(shoppingCart.getCouponDiscount())
                .deliveryCost(delivery)
                .build();
    }

    private BigDecimal getDelivery(ShoppingCart shoppingCart) {
        return deliveryCostCalculator.calculate(shoppingCart);
    }
}
