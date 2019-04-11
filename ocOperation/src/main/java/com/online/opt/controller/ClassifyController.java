package com.online.opt.controller;

import com.online.common.page.TailPage;
import com.online.common.web.JsonView;
import com.online.core.consts.domain.ConstsClassify;
import com.online.core.consts.service.IConstsClassifyService;
import com.online.opt.business.IPortalBusiness;
import com.online.opt.vo.ConstsClassifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 课程分类管理
 */
@RequestMapping("/classify")
@Controller
public class ClassifyController {
    @Autowired
    private IPortalBusiness portalBusiness;

    @Autowired
    private IConstsClassifyService constsClassifyService;

    @RequestMapping("/deleteLogic")
    @ResponseBody
    public String deleteLogic(Long id){
        constsClassifyService.deleteLogic(id);
        return new JsonView().toString();
    }

    @RequestMapping("/getById")
    @ResponseBody
    public String getById(Long id){
        ConstsClassify constsClassify = constsClassifyService.getById(id);
        return JsonView.render(constsClassify);
    }

    @RequestMapping("/doMerge")
    @ResponseBody
    public String doMerge(ConstsClassify constsClassify){
        if (constsClassify.getId() == null){
            ConstsClassify tmpEntity = constsClassifyService.getByCode(constsClassify.getCode());
            if (tmpEntity != null){
                return JsonView.render(1,"此编码已存在");
            }
            constsClassifyService.createSelectivity(constsClassify);
        }else {
            constsClassifyService.updateSelectivity(constsClassify);
        }
        return new JsonView().toString();
    }

    /**
     * 课程分类展示
     * @param queryEntity
     * @param page
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(ConstsClassify queryEntity, TailPage<ConstsClassify> page){
        ModelAndView mv = new ModelAndView("cms/classify/classifyIndex");
        mv.addObject("curNav", "classify");
        Map<String,ConstsClassifyVO> classifyVOMap = portalBusiness.queryAllClassifyMap();

        //获取所有一级分类
        List<ConstsClassify> classifyList = new ArrayList<>();
        for (ConstsClassify vo:
             classifyVOMap.values()) {
            classifyList.add(vo);
        }
        mv.addObject("classifys",classifyList);

        //获取所有二级分类
        List<ConstsClassify> subClassifys = new ArrayList<>();
        for (ConstsClassifyVO vo:
             classifyVOMap.values()) {
            subClassifys.addAll(vo.getSubClassifyList());
        }
        mv.addObject("subClassifys",subClassifys);

        return mv;
    }
}
