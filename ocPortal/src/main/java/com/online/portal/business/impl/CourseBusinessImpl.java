package com.online.portal.business.impl;

import com.online.core.consts.CourseEnum;
import com.online.core.course.domain.CourseSection;
import com.online.core.course.service.ICourseSectionService;
import com.online.portal.business.ICourseBusiness;
import com.online.portal.vo.CourseSectionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseBusinessImpl implements ICourseBusiness {

    @Autowired
    private ICourseSectionService courseSectionService;


    public List<CourseSectionVO> queryCourseSection(Long courseId) {
        List<CourseSectionVO> resultList = new ArrayList<CourseSectionVO>();
        CourseSection queryEntity = new CourseSection();
        queryEntity.setCourseId(courseId);
        queryEntity.setOnsale(CourseEnum.ONSALE.value());   //上架的
        Map<Long,CourseSectionVO> tmpMap = new HashMap<Long, CourseSectionVO>();
        List<CourseSection> sectionList = courseSectionService.queryAll(queryEntity);
        for (CourseSection item:
             sectionList) {
            if (Long.valueOf(0).equals(item.getParentId())){    //是章
                CourseSectionVO vo = new CourseSectionVO();
                BeanUtils.copyProperties(item,vo);
                tmpMap.put(vo.getId(),vo);
            }else {
                tmpMap.get(item.getParentId()).getSections().add(item);     //小节添加到章中
            }
        }
        resultList.addAll(tmpMap.values());
        return resultList;
    }
}
