package com.online.core.consts.service.impl;

import com.online.common.page.TailPage;
import com.online.common.storage.QiniuStorage;
import com.online.core.consts.dao.ConstsSiteCarouselDao;
import com.online.core.consts.domain.ConstsSiteCarousel;
import com.online.core.consts.service.IConstsSiteCarouselService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstsSiteCarouselServiceImpl implements IConstsSiteCarouselService {
    @Autowired
    private ConstsSiteCarouselDao constsSiteCarouselDao;

    public TailPage<ConstsSiteCarousel> queryPage(ConstsSiteCarousel queryEntity, TailPage<ConstsSiteCarousel> page) {
        Integer itemTotalCount = constsSiteCarouselDao.getTotalItemCount(queryEntity);
        List<ConstsSiteCarousel> items = constsSiteCarouselDao.queryPage(queryEntity,page);
        if (CollectionUtils.isNotEmpty(items)) {
            for (ConstsSiteCarousel item :
                    items) {
                String url = QiniuStorage.getUrl(item.getPicture());    // 处理图片
                item.setPicture(url);
            }
        }
        page.setItemsTotalCount(itemTotalCount);
        page.setItems(items);
        return page;
    }

    public ConstsSiteCarousel getById(Long id) {
        ConstsSiteCarousel constsSiteCarousel = constsSiteCarouselDao.getById(id);
        return constsSiteCarousel;
    }

    public void createSelectivity(ConstsSiteCarousel constsSiteCarousel) {
        constsSiteCarouselDao.createSelectivity(constsSiteCarousel);
    }

    public void updateSelectivity(ConstsSiteCarousel constsSiteCarousel) {
        constsSiteCarouselDao.updateSelectivity(constsSiteCarousel);
    }

    public void delete(ConstsSiteCarousel constsSiteCarousel) {
        constsSiteCarouselDao.delete(constsSiteCarousel);
    }
}
