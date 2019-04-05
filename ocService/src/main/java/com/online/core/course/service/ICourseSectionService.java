package com.online.core.course.service;

import com.online.core.course.domain.CourseSection;

import java.util.List;

public interface ICourseSectionService {
    /**
     * 查询所有
     * @param queryEntity
     * @return
     */
     List<CourseSection> queryAll(CourseSection queryEntity);

    /**
     * 根据id获取
     * @param sectionId
     * @return
     */
    CourseSection getById(Long sectionId);
}
