package com.online.opt.controller;

import com.online.common.page.TailPage;
import com.online.common.storage.QiniuStorage;
import com.online.common.web.JsonView;
import com.online.core.consts.domain.ConstsSiteCarousel;
import com.online.core.consts.service.IConstsSiteCarouselService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 轮播配置
 */
@RequestMapping("/carousel")
@Controller
public class SiteCarouselController {

    @Autowired
    private IConstsSiteCarouselService constsSiteCarouselService;

    @RequestMapping("/queryPage")
    public ModelAndView queryPage(ConstsSiteCarousel queryEntity, TailPage<ConstsSiteCarousel> page){
        ModelAndView mv = new ModelAndView("cms/carousel/pagelist");
        mv.addObject("curNav", "carousel");

        page.setPageSize(5);
        page = constsSiteCarouselService.queryPage(queryEntity,page);

        mv.addObject("page",page);
        mv.addObject("queryEntity",queryEntity);
        return mv;
    }

    @RequestMapping("/toMerge")
    public ModelAndView toMerge(ConstsSiteCarousel constsSiteCarousel){
        ModelAndView mv = new ModelAndView("cms/carousel/merge");
        mv.addObject("curNav","carousel");

        if (constsSiteCarousel.getId() != null){
            constsSiteCarousel = constsSiteCarouselService.getById(constsSiteCarousel.getId());
            if (null != constsSiteCarousel && StringUtils.isNotEmpty(constsSiteCarousel.getPicture())){
                String url = QiniuStorage.getUrl(constsSiteCarousel.getPicture());
                constsSiteCarousel.setPicture(url);
            }
        }
        mv.addObject("constsSiteCarousel",constsSiteCarousel);
        return mv;
    }

    /**
     * 添加修改
     * @param constsSiteCarousel
     * @param pictureImg
     * @return
     */
    @RequestMapping("/doMerge")
    public ModelAndView doMerge(ConstsSiteCarousel constsSiteCarousel, MultipartFile pictureImg){
        String key = null;
        try {
            if (null != pictureImg && pictureImg.getBytes().length > 0){
                key = QiniuStorage.uploadImage(pictureImg.getBytes());
                constsSiteCarousel.setPicture(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (constsSiteCarousel.getId() == null){
            constsSiteCarouselService.createSelectivity(constsSiteCarousel);
        }else {
            constsSiteCarouselService.updateSelectivity(constsSiteCarousel);
        }
        return new ModelAndView("redirect:/carousel/queryPage.html");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(ConstsSiteCarousel constsSiteCarousel){
        constsSiteCarouselService.delete(constsSiteCarousel);
        return new JsonView().toString();
    }
}
