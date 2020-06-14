package com.cart.domain.campaign.discount;

import java.util.List;

public interface DiscountService {
    List<Discount> getDiscountsByCategories(List<Integer> categoryIds);
}
