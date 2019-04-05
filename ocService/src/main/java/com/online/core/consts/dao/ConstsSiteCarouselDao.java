package com.online.core.consts.dao;

import com.online.core.consts.domain.ConstsSiteCarousel;

import java.util.List;

public interface ConstsSiteCarouselDao {
    List<ConstsSiteCarousel> queryCarousels(Integer count);
}
