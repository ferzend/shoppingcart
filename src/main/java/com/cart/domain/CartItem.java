package com.cart.domain;

import com.cart.domain.product.Product;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void incrementQuantityBy(int quantity) {
        this.quantity += quantity;
    }

    public int getCategoryId() {
        return product.getCategory().getCategoryId();
    }

    public String getCategoryTitle() {
        return product.getCategory().getTitle();
    }

    public String getTitle() {
        return product.getTitle();
    }

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }
}
