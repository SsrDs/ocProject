package com.online.opt.business.impl;

import com.online.core.consts.CourseEnum;
import com.online.core.consts.domain.ConstsClassify;
import com.online.core.consts.service.IConstsClassifyService;
import com.online.core.course.domain.CourseSection;
import com.online.core.course.service.ICourseSectionService;
import com.online.opt.business.IPortalBusiness;
import com.online.opt.vo.ConstsClassifyVO;
import com.online.opt.vo.CourseSectionVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortalBusinessImpl implements IPortalBusiness {

    @Autowired
    private IConstsClassifyService constsClassifyService;
    
    @Autowired
    private ICourseSectionService courseSectionService;

    @Override
    public List<ConstsClassifyVO> queryAllClassify() {
        return null;
    }

    /**
     * 获取所有分类
     */
    @Override
    public Map<String, ConstsClassifyVO> queryAllClassifyMap() {
        Map<String,ConstsClassifyVO> resultMap = new LinkedHashMap<>();
        List<ConstsClassify> classifies = constsClassifyService.queryAll();
        for (ConstsClassify item:
             classifies) {
            if ("0".equals(item.getParentCode())){      //一级分类
                ConstsClassifyVO vo = new ConstsClassifyVO();
                BeanUtils.copyProperties(item,vo);
                resultMap.put(vo.getCode(),vo);
            }else {         //二级分类
                if (null != resultMap.get(item.getParentCode())) {
                    resultMap.get(item.getParentCode()).getSubClassifyList().add(item);
                }
            }
        }
        return resultMap;
    }

    @Override
    public void prepareRecomdCourses(List<ConstsClassifyVO> classifyVOS) {

    }

    @Override
    public List<CourseSectionVO> queryCourseSection(Long id) {
        List<CourseSectionVO> resultList = new ArrayList<>();
        CourseSection queryEntity = new CourseSection();
        queryEntity.setCourseId(id);
        queryEntity.setOnsale(CourseEnum.ONSALE.value());   //上架
        Map<Long,CourseSectionVO> tmpMap = new LinkedHashMap<>();
        List<CourseSection> items = courseSectionService.queryAll(queryEntity);
        for (CourseSection item:
             items) {
            if (Long.valueOf(0).equals(item.getParentId())){    //章
                CourseSectionVO vo = new CourseSectionVO();
                BeanUtils.copyProperties(item,vo);
                tmpMap.put(vo.getId(),vo);
            }else {
                tmpMap.get(item.getParentId()).getSections().add(item);     //小节添加到章中
            }
        }
        for (CourseSectionVO vo:
             tmpMap.values()) {
            resultList.add(vo);
        }
        return resultList;
    }
}
