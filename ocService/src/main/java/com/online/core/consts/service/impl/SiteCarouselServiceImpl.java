package com.online.core.consts.service.impl;

import com.online.common.storage.QiniuStorage;
import com.online.core.consts.dao.ConstsSiteCarouselDao;
import com.online.core.consts.domain.ConstsSiteCarousel;
import com.online.core.consts.service.ISiteCarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteCarouselServiceImpl implements ISiteCarouselService {
    @Autowired
    private ConstsSiteCarouselDao constsSiteCarouselDao;

    /**
     * 加载轮播图
     * @param count
     * @return
     */
    public List<ConstsSiteCarousel> queryCarousels(Integer count) {
        List<ConstsSiteCarousel> resultList = constsSiteCarouselDao.queryCarousels(count);
        for (ConstsSiteCarousel item: resultList) {
            item.setPicture(QiniuStorage.getUrl(item.getPicture()));
//            System.out.println(item.getPicture());
        }
        return resultList;
    }
}
