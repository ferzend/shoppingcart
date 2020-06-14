package com.cart.domain;

import com.cart.domain.campaign.*;
import com.cart.domain.product.Category;
import com.cart.domain.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {
    @Mock
    private Discount discount1;
    @Mock
    private Discount discount2;
    @Mock
    private Discount discount3;
    @Mock
    private Coupon coupon;

    @Test
    public void shouldReturnNumberOfDifferentProducts() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 2);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(PHONE_BY_ELECTRONIC_CATEGORY, 1);

        int numberOfDifferentProduct = shoppingCart.getNumberOfProduct();

        assertThat(numberOfDifferentProduct).isEqualTo(3);
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
    public void campaignDiscountShouldBeEqualToApplicableDiscount_whenThereIsASuitableDiscountForCartCategories() {
        BigDecimal applicableDiscount = BigDecimal.ZERO;
        when(discount1.getApplicableDiscount(anyInt(), any(BigDecimal.class))).thenReturn(applicableDiscount);
        when(discount1.getCategoryId()).thenReturn(CATEGORY_ELECTRONIC.getCategoryId());
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 1);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 1);

        shoppingCart.applyDiscounts(discount1);

        BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();

        assertThat(campaignDiscount).isEqualTo(applicableDiscount);
    }

    @Test
    public void campaignDiscountShouldBeZero_whenThereIsNoSuitableDiscountForCartCategories() {
        when(discount1.getCategoryId()).thenReturn(CATEGORY_COMPUTER.getCategoryId());
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 7);

        shoppingCart.applyDiscounts(discount1);

        BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();

        assertThat(campaignDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void shouldApplyMostProfitableDiscount() {
        when(discount1.getApplicableDiscount(anyInt(), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
        when(discount1.getCategoryId()).thenReturn(CATEGORY_ELECTRONIC.getCategoryId());
        when(discount2.getApplicableDiscount(anyInt(), any(BigDecimal.class))).thenReturn(BigDecimal.ONE);
        when(discount2.getCategoryId()).thenReturn(CATEGORY_ELECTRONIC.getCategoryId());

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 3);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 2);

        shoppingCart.applyDiscounts(discount1, discount2);

        BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();

        assertThat(campaignDiscount).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void shouldApplyMostProfitableDiscountForEachCategory() {
        when(discount1.getApplicableDiscount(anyInt(), any(BigDecimal.class))).thenReturn(new BigDecimal("10"));
        when(discount1.getCategoryId()).thenReturn(CATEGORY_ELECTRONIC.getCategoryId());
        when(discount2.getApplicableDiscount(anyInt(), any(BigDecimal.class))).thenReturn(new BigDecimal("25"));
        when(discount2.getCategoryId()).thenReturn(CATEGORY_ELECTRONIC.getCategoryId());
        when(discount3.getApplicableDiscount(anyInt(), any(BigDecimal.class))).thenReturn(new BigDecimal("5"));
        when(discount3.getCategoryId()).thenReturn(CATEGORY_COMPUTER.getCategoryId());

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_COMPUTER_CATEGORY, 3);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 2);
        shoppingCart.addProduct(PHONE_BY_ELECTRONIC_CATEGORY, 5);

        shoppingCart.applyDiscounts(discount1, discount2, discount3);

        BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();

        assertThat(campaignDiscount).isEqualTo(new BigDecimal("30"));
    }

    @Test
    public void couponDiscountShouldBeEqualToApplicableDiscount() {
        BigDecimal applicableDiscount = BigDecimal.TEN;
        when(coupon.getApplicableDiscount(any(BigDecimal.class))).thenReturn(applicableDiscount);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_ELECTRONIC_CATEGORY, 5);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 5);

        shoppingCart.applyCoupon(coupon);

        BigDecimal couponDiscount = shoppingCart.getCouponDiscount();

        assertThat(couponDiscount).isEqualTo(applicableDiscount);
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
}