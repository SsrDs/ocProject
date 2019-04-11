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

    /**
     * 获取课程章最大的sort
     */
    Integer getMaxSort(Long courseId);

    /**
     *创建
     **/
    void createSelectivity(CourseSection courseSection);

    /**
     *批量创建
     **/
    void createList(List<CourseSection> subCourseSections);

    /**
     *根据id 进行可选性更新
     **/
    void updateSelectivity(CourseSection courseSection);

    /**
     * 物理删除
     * @param entity
     */
    void delete(CourseSection entity);
}
