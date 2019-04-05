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

    Course getById(Long courseId);
}
