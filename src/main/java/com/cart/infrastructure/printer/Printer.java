package com.cart.infrastructure.printer;

import com.cart.domain.CartSummary;

public interface Printer {
    String print(CartSummary cartSummary);
}
