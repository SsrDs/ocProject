package com.online.portal.controller;

import com.online.common.page.TailPage;
import com.online.core.consts.CourseEnum;
import com.online.core.consts.domain.ConstsClassify;
import com.online.core.consts.service.IConstsClassifyService;
import com.online.core.course.domain.Course;
import com.online.core.course.service.ICourseService;
import com.online.portal.business.IPortalBusiness;
import com.online.portal.vo.ConstsClassifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/course")
@Controller
public class CourseListController {

    @Autowired
    private IPortalBusiness portalBusiness;

    @Autowired
    private IConstsClassifyService constsClassifyService;

    @Autowired
    private ICourseService courseService;

    @RequestMapping("/list")
    public ModelAndView list(String c, String sort, TailPage<Course> page){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("list");
        String curCode = "-1";          //当前父类
        String curSubCode = "-2";       //当前分类

        //获得所有课程分类
        Map<String, ConstsClassifyVO> classifyVOMap = portalBusiness.queryAllClassifyMap();

        //获得所有一级分类
        List<ConstsClassify> classifyList = new ArrayList<ConstsClassify>();
        for (ConstsClassifyVO vo:
             classifyVOMap.values()) {
            classifyList.add(vo);
        }
        mv.addObject("classifyList",classifyList);

        //判断当前分类
        ConstsClassify constsClassify = constsClassifyService.getByCode(c);

        if(null == constsClassify){     //code值不存在，加载所有二级分类
            List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
            for (ConstsClassifyVO vo:
                 classifyVOMap.values()) {
                subClassifys.addAll(vo.getSubClassifyList());
            }
            mv.addObject("subClassifys",subClassifys);
        }else {
            if (!"0".equals(constsClassify.getParentCode())){       //当前是二级分类
                curSubCode = constsClassify.getCode();          //当前二级分类
                curCode = constsClassify.getParentCode();       //父级分类
                mv.addObject("subClassifys",classifyVOMap.get(constsClassify.getParentCode()).getSubClassifyList());    //获取当前分类的所有二级分类
            }else {     //当前是一级分类
                curCode = constsClassify.getCode();
                mv.addObject("subClassifys",classifyVOMap.get(curCode).getSubClassifyList());       //此分类下的所有二级分类
            }
        }
        mv.addObject("curCode",curCode);
        mv.addObject("curSubCode",curSubCode);

        //分页排序
        //分页的分类参数
        Course queryEntity = new Course();
        if(!"-1".equals(curCode)){
            queryEntity.setClassify(curCode);
        }
        if (!"-2".equals(curSubCode)){
            queryEntity.setSubClassify(curSubCode);
        }

        //排序参数
        if ("pop".equals(sort)){    //最热
            page.descSortField("studyCount");
        }else {
            sort = "last";
            page.descSortField("id");
        }
        mv.addObject("sort",sort);

        //分页参数
        queryEntity.setOnsale(CourseEnum.ONSALE.value());
        page = courseService.queryPage(queryEntity,page);
        mv.addObject("page",page);
        return mv;
    }

}
