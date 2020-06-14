package com.cart.infrastructure.printer;

import com.cart.domain.CartItem;
import com.cart.domain.CartSummary;
import com.cart.domain.printer.CartPrinter;
import com.cart.domain.product.Category;
import com.cart.domain.product.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemOutCartPrinterTest {

    private CartPrinter cartPrinter = new SystemOutCartPrinter();

    @Test
    public void shouldPrintSummary() {
        CartSummary summary = CartSummary.builder()
                .cartItems(Arrays.asList(CART_ITEM_1, CART_ITEM_2, CART_ITEM_3, CART_ITEM_4, CART_ITEM_5))
                .campaignDiscount(new BigDecimal("10"))
                .couponDiscount(new BigDecimal("5"))
                .deliveryCost(new BigDecimal("7.99"))
                .build();

        String printedSummary = cartPrinter.print(summary);

        assertThat(printedSummary).isEqualTo(expectedPrintedSummar);
    }

    private static final String expectedPrintedSummar =
            "Category: computer\n" +
            "mouse - 1 - 10 - 10\n" +
            "phone - 7 - 10 - 70\n" +
            "Category: electronic\n" +
            "apple - 3 - 10 - 30\n" +
            "mouse - 5 - 10 - 50\n" +
            "phone - 4 - 10 - 40\n" +
            "Total Discount: 15\n" +
            "Delivery: 7.99";

    private static final BigDecimal PRICE = new BigDecimal("10");
    private static final String PRODUCT_APPLE = "apple";
    private static final String PRODUCT_MOUSE = "mouse";
    private static final String PRODUCT_PHONE = "phone";

    private static final Category CATEGORY_ELECTRONIC = new Category(1, "electronic");
    private static final Category CATEGORY_COMPUTER = new Category(2, "computer");
    private static final CartItem CART_ITEM_1 = new CartItem(new Product(PRODUCT_APPLE, PRICE, CATEGORY_ELECTRONIC), 3);
    private static final CartItem CART_ITEM_2 = new CartItem(new Product(PRODUCT_MOUSE, PRICE, CATEGORY_ELECTRONIC), 5);
    private static final CartItem CART_ITEM_3 = new CartItem(new Product(PRODUCT_PHONE, PRICE, CATEGORY_ELECTRONIC), 4);

    private static final CartItem CART_ITEM_4 = new CartItem(new Product(PRODUCT_MOUSE, PRICE, CATEGORY_COMPUTER), 1);
    private static final CartItem CART_ITEM_5 = new CartItem(new Product(PRODUCT_PHONE, PRICE, CATEGORY_COMPUTER), 7);

}