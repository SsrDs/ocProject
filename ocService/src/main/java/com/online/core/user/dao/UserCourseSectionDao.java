package com.online.core.user.dao;

import com.online.common.page.TailPage;
import com.online.core.course.domain.CourseSection;
import com.online.core.user.domain.UserCourseSection;
import com.online.core.user.domain.UserCourseSectionDto;

import java.util.List;

public interface UserCourseSectionDao {

    /**
     * 获取最新的
     * @param queryEntity
     * @return
     */
    UserCourseSection queryLatest(UserCourseSection queryEntity);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    CourseSection getById(Long id);

    /**
     * 根据id更新
     * @param result
     */
    void update(UserCourseSection result);

    /**
     * 获取总数
     * @param queryEntity
     * @return
     */
    Integer getTotalItemCount(UserCourseSection queryEntity);

    /**
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    List<UserCourseSectionDto> queryPage(UserCourseSection queryEntity, TailPage<UserCourseSectionDto> page);
}
