package com.cart.domain.printer;

import com.cart.domain.CartSummary;

public interface CartPrinter {
    String print(CartSummary cartSummary);
}
