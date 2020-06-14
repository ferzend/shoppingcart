package com.cart.domain.campaign;

import com.cart.domain.campaign.Coupon;
import com.cart.domain.campaign.Discount;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CampaignService {
    Map<Integer, List<Discount>> getCapaignsByCategories(List<Integer> categoryIds);
    Optional<Coupon> getCoupon();
}
