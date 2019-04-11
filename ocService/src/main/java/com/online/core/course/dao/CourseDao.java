package com.online.core.course.dao;

import com.online.common.page.TailPage;
import com.online.core.course.domain.Course;
import com.online.core.course.domain.CourseQueryDto;

import java.util.List;

public interface CourseDao {
    /**
     * 根据条件查询所有
     * @param queryDto
     * @return
     */
    List<Course> queryList(CourseQueryDto queryDto);

    /**
     * 获取总记录数
     * @param queryEntity
     * @return
     */
    Integer getTotalItemsCount(Course queryEntity);

    /**
     * 分页查询
     * @param queryEntity
     * @param page
     * @return
     */
    List<Course> queryPage(Course queryEntity, TailPage<Course> page);

    /**
     * 根据id获取
     * @param courseId
     * @return
     */
    Course getById(Long courseId);

    /**
     * 修改用户
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
