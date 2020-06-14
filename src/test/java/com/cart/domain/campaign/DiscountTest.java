package com.cart.domain.campaign;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountTest {

    @Test
    public void applicableDiscountShouldBeZero_whenGivenNumberOfProductLessThanMinNumberOfProduct_forFixedDiscount() {
        int minNumberOfProduct = 3;
        int numberOfProduct = 2;

        Discount discount = new FixedDiscount(1, 20.0, minNumberOfProduct);

        BigDecimal applicableDiscount = discount.getApplicableDiscount(numberOfProduct, BigDecimal.valueOf(200.0));

        assertThat(applicableDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void applicableDiscountShouldBeGivenDiscount_whenGivenNumberOfProductEqualMinNumberOfProduct_forFixedDiscount() {
        int minNumberOfProduct = 3;
        int numberOfProduct = 3;

        Discount discount = new FixedDiscount(1, 20.0, minNumberOfProduct);

        BigDecimal applicableDiscount = discount.getApplicableDiscount(numberOfProduct, BigDecimal.valueOf(200.0));

        assertThat(applicableDiscount).isEqualTo(BigDecimal.valueOf(20.0));
    }

    @Test
    public void applicableDiscountShouldBeGivenDiscount_whenGivenNumberOfProductGreaterThanMinNumberOfProduct_forFixedDiscount() {
        int minNumberOfProduct = 3;
        int numberOfProduct = 4;

        Discount discount = new FixedDiscount(1, 20.0, minNumberOfProduct);

        BigDecimal applicableDiscount = discount.getApplicableDiscount(numberOfProduct, BigDecimal.valueOf(200.0));

        assertThat(applicableDiscount).isEqualTo(BigDecimal.valueOf(20.0));
    }

    @Test
    public void applicableDiscountShouldBeZero_whenGivenNumberOfProductLessThanMinNumberOfProduct_forPercentageDiscount() {
        int minNumberOfProduct = 3;
        int numberOfProduct = 2;

        Discount discount = new PercentageDiscount(1, 20.0, minNumberOfProduct);

        BigDecimal applicableDiscount = discount.getApplicableDiscount(numberOfProduct, BigDecimal.valueOf(200.0));

        assertThat(applicableDiscount).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void applicableDiscountShouldBeRateOfGivenAmount_whenGivenNumberOfProductEqualMinNumberOfProduct_forPercentageDiscount() {
        int minNumberOfProduct = 3;
        int numberOfProduct = 3;

        Discount discount = new PercentageDiscount(1, 20.0, minNumberOfProduct);

        BigDecimal applicableDiscount = discount.getApplicableDiscount(numberOfProduct, BigDecimal.valueOf(200));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal("40.0"));
    }

    @Test
    public void applicableDiscountShouldBeRateOfGivenAmount_whenGivenNumberOfProductGreaterThanMinNumberOfProduct_forPercentageDiscount() {
        int minNumberOfProduct = 3;
        int numberOfProduct = 4;

        Discount discount = new PercentageDiscount(1, 20.0, minNumberOfProduct);

        BigDecimal applicableDiscount = discount.getApplicableDiscount(numberOfProduct, BigDecimal.valueOf(200));

        assertThat(applicableDiscount).isEqualTo(new BigDecimal("40.0"));
    }
}
