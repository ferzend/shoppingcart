package com.cart.infrastructure.printer;

import com.cart.domain.CartItem;
import com.cart.domain.CartSummary;
import com.cart.domain.printer.CartPrinter;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class SystemOutCartPrinter implements CartPrinter {
    @Override
    public String print(CartSummary cartSummary) {
        Map<String, List<CartItem>> productByCategoryMap = cartSummary.getCartItems().stream().collect(groupingBy(CartItem::getCategoryTitle));

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, List<CartItem>> productCategory : productByCategoryMap.entrySet()) {
            builder.append("Category: ").append(productCategory.getKey()).append("\n");

            for (CartItem cartItem : productCategory.getValue()) {
                builder.append(cartItem.getTitle()).append(" - ")
                        .append(cartItem.getQuantity()).append(" - ")
                        .append(cartItem.getPrice()).append(" - ")
                        .append(cartItem.getTotalPrice()).append("\n");
            }
        }

        builder.append("Total Discount: ").append(cartSummary.getCampaignDiscount().add(cartSummary.getCouponDiscount())).append("\n");
        builder.append("Delivery: ").append(cartSummary.getDeliveryCost());

        String printedSummary = builder.toString();
        System.out.print(printedSummary);

        return printedSummary;
    }
}
