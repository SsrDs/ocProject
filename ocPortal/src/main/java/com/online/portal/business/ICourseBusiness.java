package com.online.portal.business;

import com.online.portal.vo.CourseSectionVO;

import java.util.List;

public interface ICourseBusiness {
    /**
     * 获取课程章节
     * @param courseId
     * @return
     */
    List<CourseSectionVO> queryCourseSection(Long courseId);
}
