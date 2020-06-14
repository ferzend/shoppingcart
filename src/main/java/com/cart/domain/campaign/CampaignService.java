package com.cart.domain.campaign;

import com.cart.domain.campaign.coupon.Coupon;
import com.cart.domain.campaign.discount.Discount;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CampaignService {
    Map<Integer, List<Discount>> getCampaignsByCategories(List<Integer> categoryIds);
    Optional<Coupon> getCoupon();
}
