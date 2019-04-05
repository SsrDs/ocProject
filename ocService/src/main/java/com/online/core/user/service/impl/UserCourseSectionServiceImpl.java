package com.online.core.user.service.impl;

import com.online.common.page.TailPage;
import com.online.core.course.domain.CourseSection;
import com.online.core.user.dao.UserCourseSectionDao;
import com.online.core.user.domain.UserCourseSection;
import com.online.core.user.domain.UserCourseSectionDto;
import com.online.core.user.service.IUserCourseSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseSectionServiceImpl implements IUserCourseSectionService {

    @Autowired
    private UserCourseSectionDao userCourseSectionDao;

    public UserCourseSection queryLatest(UserCourseSection queryEntity) {
        return userCourseSectionDao.queryLatest(queryEntity);
    }

    public CourseSection getById( Long id) {
        return userCourseSectionDao.getById(id);
    }

    public void update(UserCourseSection result) {
        userCourseSectionDao.update(result);
    }

    public TailPage<UserCourseSectionDto> queryPage(UserCourseSection queryEntity, TailPage<UserCourseSectionDto> page) {
        Integer itemTotalCount = userCourseSectionDao.getTotalItemCount(queryEntity);
        List<UserCourseSectionDto> items = userCourseSectionDao.queryPage(queryEntity,page);
        page.setItemsTotalCount(itemTotalCount);
        page.setItems(items);
        return page;
    }
}
