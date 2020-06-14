package com.cart.domain;

import com.cart.domain.campaign.*;
import com.cart.domain.product.Category;
import com.cart.domain.product.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartTest {

    @Test
    public void shouldReturnNumberOfDifferentProducts() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 2);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(PHONE_BY_ELECTRONIC_CATEGORY, 1);

        int numberOfDifferentProdcut = shoppingCart.getNumberOfProduct();

        assertThat(numberOfDifferentProdcut).isEqualTo(3);
    }

    @Test
    public void shouldReturnNumberOfDifferentCategories() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 2);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(PHONE_BY_ELECTRONIC_CATEGORY, 1);

        int numberOfDifferentCategory = shoppingCart.getNumberOfCategory();

        assertThat(numberOfDifferentCategory).isEqualTo(2);
    }

    @Test
    public void campaignDiscountShouldBeZero_whenNumberOfProductNotSuitable() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 1);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 1);

        shoppingCart.applyDiscounts(ELECTRONIC_AMOUNT_5_5, ELECTRONIC_RATE_20_3, ELECTRONIC_RATE_50_5);

        BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();

        assertThat(campaignDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void campaignDiscountShouldBeZero_whenThereIsNoSuitableDiscountForCartCategories() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 7);

        shoppingCart.applyDiscounts(COMPUTER_AMOUNT_5_5, COMPUTER_RATE_20_3, COMPUTER_RATE_50_5);

        BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();

        assertThat(campaignDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void shouldApplyMostProfitableDiscount_whenCartContainsOneCategory() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 3);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 2);

        shoppingCart.applyDiscounts(ELECTRONIC_AMOUNT_5_5, ELECTRONIC_RATE_20_3, ELECTRONIC_RATE_50_5);

        BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();

        assertThat(campaignDiscount).isEqualTo(new BigDecimal("25.0"));
    }

    @Test
    public void shouldApplyMostProfitableDiscountForEachCategory_whenCartContainsMoreThanOneCategory() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_COMPUTER_CATEGORY, 3);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 2);
        shoppingCart.addProduct(PHONE_BY_ELECTRONIC_CATEGORY, 5);

        shoppingCart.applyDiscounts(COMPUTER_AMOUNT_5_5, ELECTRONIC_RATE_20_3, ELECTRONIC_RATE_50_5);

        BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();

        assertThat(campaignDiscount).isEqualTo(new BigDecimal("30.0"));
    }

    @Test
    public void couponDiscountShouldBeZero_whenMinPurchaseAmountNotSuitable() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 3);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 1);

        shoppingCart.applyCoupon(COUPON);

        BigDecimal couponDicount = shoppingCart.getCouponDiscount();

        assertThat(couponDicount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void couponDiscountShouldBeGivenDiscount_whenMinPurchaseAmountNotSuitable() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 5);

        shoppingCart.applyCoupon(COUPON);

        BigDecimal couponDiscount = shoppingCart.getCouponDiscount();

        assertThat(couponDiscount).isEqualTo(new BigDecimal("5.0"));
    }

    private static final BigDecimal PRICE = new BigDecimal("10");
    private static final String PRODUCT_APPLE = "apple";
    private static final String PRODUCT_MOUSE = "mouse";
    private static final String PRODUCT_PHONE = "phone";
    private static final Category CATEGORY_ELECTRONIC = new Category(1, "electronic");
    private static final Category CATEGORY_COMPUTER = new Category(2, "computer");

    private static final Product APPLE_BY_COMPUTER_CATEGORY = new Product(PRODUCT_APPLE, PRICE, CATEGORY_COMPUTER);
    private static final Product MOUSE_BY_COMPUTER_CATEGORY = new Product(PRODUCT_MOUSE, PRICE, CATEGORY_COMPUTER);
    private static final Product APPLE_BY_ELECTRONIC_CATEGORY = new Product(PRODUCT_APPLE, PRICE, CATEGORY_ELECTRONIC);
    private static final Product MOUSE_BY_ELECTRONIC_CATEGORY = new Product(PRODUCT_MOUSE, PRICE, CATEGORY_ELECTRONIC);
    private static final Product PHONE_BY_ELECTRONIC_CATEGORY = new Product(PRODUCT_PHONE, PRICE, CATEGORY_ELECTRONIC);

    private static final Discount ELECTRONIC_RATE_20_3 = new PercentageDiscount(CATEGORY_ELECTRONIC.getCategoryId(), 20.0, 3);
    private static final Discount ELECTRONIC_RATE_50_5 = new PercentageDiscount(CATEGORY_ELECTRONIC.getCategoryId(), 50.0, 5);
    private static final Discount ELECTRONIC_AMOUNT_5_5 = new FixedDiscount(CATEGORY_ELECTRONIC.getCategoryId(), 5.0, 5);

    private static final Discount COMPUTER_RATE_20_3 = new PercentageDiscount(CATEGORY_COMPUTER.getCategoryId(), 20.0, 3);
    private static final Discount COMPUTER_RATE_50_5 = new PercentageDiscount(CATEGORY_COMPUTER.getCategoryId(), 50.0, 5);
    private static final Discount COMPUTER_AMOUNT_5_5 = new FixedDiscount(CATEGORY_COMPUTER.getCategoryId(), 5.0, 5);

    private static final Coupon COUPON = new FixedCoupon(100.0, 5.0);
}