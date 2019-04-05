package com.online.core.consts.service;

import com.online.core.consts.domain.ConstsSiteCarousel;

import java.util.List;

public interface ISiteCarouselService {
    List<ConstsSiteCarousel> queryCarousels(Integer count);
}
