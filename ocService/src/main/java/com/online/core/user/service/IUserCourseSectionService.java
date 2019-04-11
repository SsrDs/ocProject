package com.online.core.user.service;

import com.online.common.page.TailPage;
import com.online.core.course.domain.CourseSection;
import com.online.core.user.domain.UserCourseSection;
import com.online.core.user.domain.UserCourseSectionDto;

public interface IUserCourseSectionService {
    /**
     * 获取最新的学习记录
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
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    TailPage<UserCourseSectionDto> queryPage(UserCourseSection queryEntity, TailPage<UserCourseSectionDto> page);

    /**
     * 添加
     * @param userCourseSection
     */
    void createSelectivity(UserCourseSection userCourseSection);
}
