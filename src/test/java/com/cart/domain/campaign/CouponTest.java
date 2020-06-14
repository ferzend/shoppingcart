package com.cart.domain.campaign;

import com.cart.domain.campaign.coupon.Coupon;
import com.cart.domain.campaign.coupon.FixedCoupon;
import com.cart.domain.campaign.coupon.PercentageCoupon;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponTest {

    @Test
    public void applicableDiscountShouldBeZero_whenGivenAmountLessThanMinPurchaseAmount_forFixedCoupon() {
        Coupon coupon = new FixedCoupon(new BigDecimal(100), new BigDecimal(5));

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(99));

        assertThat(applicableDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void applicableDiscountShouldBeGivenDiscount_whenGivenAmountEqualMinPurchaseAmount_forFixedCoupon() {
        Coupon coupon = new FixedCoupon(new BigDecimal(100), new BigDecimal(5));

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(100));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal(5));
    }

    @Test
    public void applicableDiscountShouldBeGivenDiscount_whenGivenAmountGreaterThanMinPurchaseAmount_forFixedCoupon() {
        Coupon coupon = new FixedCoupon(new BigDecimal(100), new BigDecimal(5));

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(101));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal(5));
    }

    @Test
    public void applicableDiscountShouldBeZero_whenGivenAmountLessThanMinPurchaseAmount_forPercentageCoupon() {
        Coupon coupon = new PercentageCoupon(new BigDecimal(100), new BigDecimal(5));

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(99));

        assertThat(applicableDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void applicableDiscountShouldBeRateOfGivenAmount_whenGivenAmountEqualMinPurchaseAmount_forPercentageCoupon() {
        Coupon coupon = new PercentageCoupon(new BigDecimal(200), new BigDecimal(5));

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(200));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal(10));
    }

    @Test
    public void applicableDiscountShouldBeRateOfGivenAmount_whenGivenAmountGreaterThanMinPurchaseAmount_forPercentageCoupon() {
        Coupon coupon = new PercentageCoupon(new BigDecimal(200), new BigDecimal(5));

        BigDecimal applicableDiscount = coupon.getApplicableDiscount(new BigDecimal(220));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal(11));
    }
}