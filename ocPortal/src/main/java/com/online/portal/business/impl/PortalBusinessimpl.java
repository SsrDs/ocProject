package com.online.portal.business.impl;

import com.online.core.consts.domain.ConstsClassify;
import com.online.core.consts.service.IConstsClassifyService;
import com.online.core.course.domain.Course;
import com.online.core.course.domain.CourseQueryDto;
import com.online.core.course.service.ICourseService;
import com.online.portal.business.IPortalBusiness;
import com.online.portal.vo.ConstsClassifyVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页业务层
 */
@Service
public class PortalBusinessimpl implements IPortalBusiness {
    @Autowired
    private IConstsClassifyService constsClassifyService;

    @Autowired
    private ICourseService courseService;

    /**
     * 获取所有，包括一级分类和二级分类
     * @return
     */
    public List<ConstsClassifyVO> queryAllClassify() {
        List<ConstsClassifyVO> resultList = new ArrayList<ConstsClassifyVO>();
        for(ConstsClassifyVO vo : this.queryAllClassifyMap().values()){
            resultList.add(vo);
        }
        return resultList;
    }

    /**
     * 获取所有分类
     * @return
     */
    public Map<String, ConstsClassifyVO> queryAllClassifyMap() {
        Map<String,ConstsClassifyVO> resultMap = new LinkedHashMap<String, ConstsClassifyVO>();
        List<ConstsClassify> classifies = constsClassifyService.queryAll();
        for (ConstsClassify c : classifies) {
            if("0".equals(c.getParentCode())){      //一级分类
                ConstsClassifyVO vo = new ConstsClassifyVO();
                BeanUtils.copyProperties(c,vo);
                resultMap.put(vo.getCode(),vo);
            }else {       //二级分类
                if(null != resultMap.get(c.getParentCode())){
                    resultMap.get(c.getParentCode()).getSubClassifyList().add(c);   //添加到子分类中
                }
            }
        }
        return resultMap;
    }

    /**
     * 为分类设置课程推荐
     * @param classifyVOS
     */
    public void prepareRecomdCourses(List<ConstsClassifyVO> classifyVOS) {
        if (CollectionUtils.isNotEmpty(classifyVOS)){
            for (ConstsClassifyVO vo : classifyVOS) {
                CourseQueryDto queryDto = new CourseQueryDto();
                queryDto.setCount(5);
                queryDto.descSortField("weight");
                queryDto.setClassify(vo.getCode());     //课程分类

                List<Course> courseList = this.courseService.queryList(queryDto);
                if (CollectionUtils.isNotEmpty(courseList)){
                    vo.setRecomdCourseList(courseList);
                }
            }
        }
    }


}
