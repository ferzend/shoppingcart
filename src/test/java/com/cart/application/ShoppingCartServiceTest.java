package com.cart.application;

import com.cart.domain.CartItem;
import com.cart.domain.CartSummary;
import com.cart.domain.ShoppingCart;
import com.cart.domain.campaign.AmountCoupon;
import com.cart.domain.campaign.Coupon;
import com.cart.domain.campaign.Discount;
import com.cart.domain.campaign.RateDiscount;
import com.cart.domain.delivery.DeliveryCostCalculator;
import com.cart.domain.product.Category;
import com.cart.domain.product.Product;
import com.cart.infrastructure.campaign.CampaignService;
import com.cart.infrastructure.printer.Printer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {
    @Mock
    private DeliveryCostCalculator deliveryCostCalculator;
    @Mock
    private CampaignService campaignService;
    @Mock
    private Printer printer;
    @Mock
    private ShoppingCart shoppingCart;

    private ShoppingCartService shoppingCartService;

    @Before
    public void setUp() throws Exception {
        when(campaignService.getCapaignsByCategories(any())).thenReturn(DISCOUNT_MAP);
        when(campaignService.getCoupon()).thenReturn(Optional.ofNullable(COUPON));
        when(shoppingCart.getProducts()).thenReturn(Arrays.asList(CART_ITEM_1, CART_ITEM_2, CART_ITEM_3));
        when(shoppingCart.getCampaignDiscount()).thenReturn(new BigDecimal("10"));
        when(shoppingCart.getCouponDiscount()).thenReturn(new BigDecimal("5"));
        when(deliveryCostCalculator.calculate(shoppingCart)).thenReturn(new BigDecimal("7.99"));

        shoppingCartService = new ShoppingCartService(deliveryCostCalculator, campaignService, printer);
    }

    @Test
    public void shouldApplyDiscountBeforeApplyCoupon_whenCalculateSummary() {
        shoppingCartService.calculateSummary(shoppingCart);

        InOrder order = inOrder(shoppingCart);

        order.verify(shoppingCart).applyDiscounts(DISCOUNT_MAP);
        order.verify(shoppingCart).applyCoupon(COUPON);
    }

    @Test
    public void shouldPrintSummary() {
        CartSummary summary = shoppingCartService.calculateSummary(shoppingCart);

        verify(printer).print(summary);
        assertThat(summary.toString()).isEqualTo(expectedSummary().toString());
    }

    private CartSummary expectedSummary() {
        return CartSummary.builder()
                .cartItems(Arrays.asList(CART_ITEM_1, CART_ITEM_2, CART_ITEM_3))
                .couponDiscount(new BigDecimal("5"))
                .campaignDiscount(new BigDecimal("10"))
                .deliveryAmount(new BigDecimal("7.99"))
                .build();
    }

    private static final Discount discount = new RateDiscount(1, 10.0, 3);
    private static final Map<Integer, List<Discount>> DISCOUNT_MAP = new HashMap<Integer, List<Discount>>() {
        {
            put(1, Collections.singletonList(discount));
        }
    };
    private static final Coupon COUPON = new AmountCoupon(100d, 5d);

    private static final BigDecimal PRICE = new BigDecimal("10");
    private static final String PRODUCT_APPLE = "apple";
    private static final String PRODUCT_MOUSE = "mouse";
    private static final String PRODUCT_PHONE = "phone";

    private static final Category CATEGORY_ELECTRONIC = new Category(1, "electronic");
    private static final CartItem CART_ITEM_1 = new CartItem(new Product(PRODUCT_APPLE, PRICE, CATEGORY_ELECTRONIC), 3);
    private static final CartItem CART_ITEM_2 = new CartItem(new Product(PRODUCT_MOUSE, PRICE, CATEGORY_ELECTRONIC), 5);
    private static final CartItem CART_ITEM_3 = new CartItem(new Product(PRODUCT_PHONE, PRICE, CATEGORY_ELECTRONIC), 4);


}