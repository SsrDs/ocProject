package com.online.core.course.service;

import com.online.common.page.TailPage;
import com.online.core.course.domain.Course;
import com.online.core.course.domain.CourseQueryDto;

import java.util.List;

public interface ICourseService {
    /**
     * 查询所有
     * @param queryDto
     * @return
     */
    List<Course> queryList(CourseQueryDto queryDto);

    /**
     * 分页查询
     * @param queryEntity
     * @param page
     * @return
     */
    TailPage<Course> queryPage(Course queryEntity, TailPage<Course> page);

    /**
     * 根据ID查询
     * @param courseId
     * @return
     */
    Course getById(Long courseId);

    /**
     * 修改课程
     * @param entity
     */
    void updateSelectivity(Course entity);

    /**
     * 物理删除
     * @param entity
     */
    void delete(Course entity);

    /**
     * 添加
     * @param course
     */
    void createSelectivity(Course course);
}
