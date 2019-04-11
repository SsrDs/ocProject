package com.online.core.course.dao;

import com.online.core.course.domain.CourseSection;

import java.util.List;

public interface CourseSectionDao {
    /**
     * 获取所有
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
     * @param courseId
     * @return
     */
    Integer getMaxSort(Long courseId);

    /**
     * 创建
     * @param courseSection
     */
    void createSelectivity(CourseSection courseSection);

    /**
     * 批量创建
     * @param subCourseSections
     */
    void createList(List<CourseSection> subCourseSections);

    /**
     * 根据id进行可选性更新
     * @param courseSection
     */
    void updateSelectivity(CourseSection courseSection);

    /**
     * 物理删除
     * @param entity
     */
    void delete(CourseSection entity);
}
