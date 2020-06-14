package com.cart.domain.campaign;

import com.cart.domain.campaign.coupon.Coupon;
import com.cart.domain.campaign.discount.Discount;

import java.util.List;
import java.util.Optional;

public interface CampaignService {
    List<Discount> getDiscountsByCategories(List<Integer> categoryIds);
    Optional<Coupon> getCoupon();
}
