package com.online.core.consts.dao;

import com.online.common.page.TailPage;
import com.online.core.consts.domain.ConstsSiteCarousel;

import java.util.List;

public interface ConstsSiteCarouselDao {
    List<ConstsSiteCarousel> queryCarousels(Integer count);

    /**
     * 获取总记录数
     * @param queryEntity
     * @return
     */
    Integer getTotalItemCount(ConstsSiteCarousel queryEntity);

    /**
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    List<ConstsSiteCarousel> queryPage(ConstsSiteCarousel queryEntity, TailPage<ConstsSiteCarousel> page);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    ConstsSiteCarousel getById(Long id);

    /**
     * 添加图片
     * @param constsSiteCarousel
     */
    void createSelectivity(ConstsSiteCarousel constsSiteCarousel);

    /**
     * 修改图片
     * @param constsSiteCarousel
     */
    void updateSelectivity(ConstsSiteCarousel constsSiteCarousel);

    /**
     * 物理删除
     * @param constsSiteCarousel
     */
    void delete(ConstsSiteCarousel constsSiteCarousel);
}
