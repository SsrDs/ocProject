package com.online.core.course.service;

import com.online.common.page.TailPage;
import com.online.core.course.domain.CourseComment;

public interface ICourseCommentService {

    /**
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    TailPage<CourseComment> queryPage(CourseComment queryEntity, TailPage<CourseComment> page);

    /**
     * 创建
     * @param courseComment
     */
    void createSelectivity(CourseComment courseComment);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    CourseComment getById(Long id);

    /**
     * 物理删除
     * @param entity
     */
    void delete(CourseComment entity);
}
