package com.cart.domain.campaign;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponTest {

    @Test
    public void applicableDiscountShouldBeZero_whenGivenAmountLessThanMinPurchaseAmount_forAmountCoupon() {
        Coupon coupon = new AmountCoupon(100.0, 5.0);

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(99));

        assertThat(applicableDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void applicableDiscountShouldBeGivenDiscount_whenGivenAmountEqualMinPurchaseAmount_forAmountCoupon() {
        Coupon coupon = new AmountCoupon(100.0, 5.0);

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(100));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal("5.0"));
    }

    @Test
    public void applicableDiscountShouldBeGivenDiscount_whenGivenAmountGreaterThanMinPurchaseAmount_forAmountCoupon() {
        Coupon coupon = new AmountCoupon(100.0, 5.0);

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(101));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal("5.0"));
    }

    @Test
    public void applicableDiscountShouldBeZero_whenGivenAmountLessThanMinPurchaseAmount_forRateCoupon() {
        Coupon coupon = new RateCoupon(100.0, 5.0);

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(99));

        assertThat(applicableDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void applicableDiscountShouldBeRateOfGivenAmount_whenGivenAmountEqualMinPurchaseAmount_forRateCoupon() {
        Coupon coupon = new RateCoupon(200.0, 5.0);

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(200));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal("10.0"));
    }

    @Test
    public void applicableDiscountShouldBeRateOfGivenAmount_whenGivenAmountGreaterThanMinPurchaseAmount_forRateCoupon() {
        Coupon coupon = new RateCoupon(200.0, 5.0);

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(201));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal("10.1"));
    }
}