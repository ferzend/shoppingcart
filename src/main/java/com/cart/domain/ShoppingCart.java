package com.cart.domain;

import com.cart.domain.campaign.Coupon;
import com.cart.domain.campaign.Discount;
import com.cart.domain.product.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ShoppingCart {
    private List<CartItem> cartItems = new ArrayList<>();

    private BigDecimal campaignDiscount = BigDecimal.ZERO;
    private BigDecimal couponDiscount = BigDecimal.ZERO;

    public void addProduct(Product product, int quantity) {
        Optional<CartItem> cartItem = getCartItem(product);
        if (cartItem.isPresent()) {
            cartItem.get().incrementQuantityBy(quantity);
        } else {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    private Optional<CartItem> getCartItem(Product product) {
        return cartItems.stream().filter(item-> item.getTitle().equals(product.getTitle())).findFirst();
    }

    public void applyDiscounts(Discount... discounts) {
        applyDiscounts(Arrays.stream(discounts).collect(groupingBy(Discount::getCategoryId)));
    }

    public void applyDiscounts(Map<Integer, List<Discount>> discountMap) {
        for (Map.Entry<Integer, List<Discount>> discountGroupByCategory : discountMap.entrySet()) {
            BigDecimal mostProfitableDiscount = BigDecimal.ZERO;
            for (Discount discount : discountGroupByCategory.getValue()) {
                BigDecimal applicableDiscount = getApplicableDiscount(discount);
                if (applicableDiscount.compareTo(mostProfitableDiscount) > 0) {
                    mostProfitableDiscount = applicableDiscount;
                }
            }

            campaignDiscount = campaignDiscount.add(mostProfitableDiscount);
        }
    }

    private BigDecimal getApplicableDiscount(Discount discount) {
        List<CartItem> productsByCategory = getProductsByCategory(discount.getCategoryId());
        if (productsByCategory.isEmpty()) {
            return BigDecimal.ZERO;
        }
        int numberOfProduct = productsByCategory.stream().map(CartItem::getQuantity).reduce(Integer::sum).get();
        BigDecimal totalAmount = productsByCategory.stream().map(CartItem::getTotalPrice).reduce(BigDecimal::add).get();
        return discount.getApplicableDiscount(numberOfProduct, totalAmount);

    }

    private List<CartItem> getProductsByCategory(int categoryId) {
        return cartItems.stream().filter(item-> item.getCategoryId() == categoryId).collect(Collectors.toList());
    }

    public void applyCoupon(Coupon coupon) {
        couponDiscount = coupon.getApplicableDiscount(getTotalAmount().subtract(campaignDiscount));
    }

    private BigDecimal getTotalAmount() {
        return cartItems.stream().map(CartItem::getTotalPrice).reduce(BigDecimal::add).get();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    public int getNumberOfCategory() {
        return (int) cartItems.stream().map(CartItem::getCategoryId).distinct().count();
    }

    public List<Integer> getCategories() {
        return cartItems.stream().map(CartItem::getCategoryId).distinct().collect(Collectors.toList());
    }

    public int getNumberOfProduct() {
        return (int) cartItems.stream().map(CartItem::getTitle).distinct().count();
    }

    public BigDecimal getTotalAmountAfterDiscounts() {
        return getTotalAmount().subtract(campaignDiscount).subtract(couponDiscount);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getCampaignDiscount() {
        return campaignDiscount;
    }

    public BigDecimal getCouponDiscount() {
        return couponDiscount;
    }
}
