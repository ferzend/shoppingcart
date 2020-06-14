package com.cart.domain.delivery;

import com.cart.domain.ShoppingCart;
import com.cart.domain.product.Category;
import com.cart.domain.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryCostCalculatorTest {
    @Mock
    private DeliveryService deliveryService;

    private DeliveryCostCalculator deliveryCostCalculator;

    @Before
    public void setUp() throws Exception {
        when(deliveryService.getDeliveryCost()).thenReturn(new DeliveryCost(new BigDecimal("2"), new BigDecimal("1"), new BigDecimal("2.99")));
        deliveryCostCalculator = new DeliveryCostCalculator(deliveryService);
    }

    @Test
    public void deliveryCostShouldBeZero_whenShoppingCartIsEmpty() {
        ShoppingCart shoppingCart = new ShoppingCart();

        BigDecimal deliveryCost = deliveryCostCalculator.calculate(shoppingCart);

        assertThat(deliveryCost).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void deliveryCostShouldCalculatedFor1CategoryAnd1Product_whenCartContains1Product() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_COMPUTER_CATEGORY, 3);

        BigDecimal deliveryCost = deliveryCostCalculator.calculate(shoppingCart);

        assertThat(deliveryCost).isEqualTo(new BigDecimal("5.99"));
    }

    @Test
    public void deliveryCostShouldCalculatedFor1CategoryAnd2Product_whenCartContains2DifferentProduct() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_COMPUTER_CATEGORY, 3);
        shoppingCart.addProduct(MOUSE_BY_COMPUTER_CATEGORY, 3);

        BigDecimal deliveryCost = deliveryCostCalculator.calculate(shoppingCart);

        assertThat(deliveryCost).isEqualTo(new BigDecimal("6.99"));
    }

    @Test
    public void deliveryCostShouldCalculatedFor2CategoryAnd2Product_whenCartContains2DifferentProduct_in2DifferentCategory() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addProduct(APPLE_BY_COMPUTER_CATEGORY, 3);
        shoppingCart.addProduct(MOUSE_BY_ELECTRONIC_CATEGORY, 3);

        BigDecimal deliveryCost = deliveryCostCalculator.calculate(shoppingCart);

        assertThat(deliveryCost).isEqualTo(new BigDecimal("8.99"));
    }

    private static final BigDecimal PRICE = new BigDecimal("10");
    private static final String PRODUCT_APPLE = "apple";
    private static final String PRODUCT_MOUSE = "mouse";

    private static final Category CATEGORY_ELECTRONIC = new Category(1, "electronic");
    private static final Category CATEGORY_COMPUTER = new Category(2, "computer");

    private static final Product APPLE_BY_COMPUTER_CATEGORY = new Product(PRODUCT_APPLE, PRICE, CATEGORY_COMPUTER);
    private static final Product MOUSE_BY_COMPUTER_CATEGORY = new Product(PRODUCT_MOUSE, PRICE, CATEGORY_COMPUTER);
    private static final Product MOUSE_BY_ELECTRONIC_CATEGORY = new Product(PRODUCT_MOUSE, PRICE, CATEGORY_ELECTRONIC);
}