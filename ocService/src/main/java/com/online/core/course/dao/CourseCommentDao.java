package com.online.core.course.dao;

import com.online.common.page.TailPage;
import com.online.core.course.domain.CourseComment;

import java.util.List;

public interface CourseCommentDao {
    /**
     * 获取总数量
     * @param queryEntity
     * @return
     */
    Integer getTotalItemsCount(CourseComment queryEntity);

    /**
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    List<CourseComment> queryPage(CourseComment queryEntity, TailPage<CourseComment> page);

    /**
     * 创建
     * @param courseComment
     */
    void createSelectivity(CourseComment courseComment);

    /**
     * 根据id获取
     * @return
     */
    CourseComment getById(Long id);
}
