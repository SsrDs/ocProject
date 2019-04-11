package com.online.core.course.service.impl;

import com.online.common.page.TailPage;
import com.online.core.course.dao.CourseCommentDao;
import com.online.core.course.domain.CourseComment;
import com.online.core.course.service.ICourseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCommentServiceImpl implements ICourseCommentService {
    @Autowired
    private CourseCommentDao commentDao;

    public TailPage<CourseComment> queryPage(CourseComment queryEntity, TailPage<CourseComment> page) {
        Integer itemsTotalCount =   commentDao.getTotalItemsCount(queryEntity);
        List<CourseComment> items = commentDao.queryPage(queryEntity,page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    public void createSelectivity(CourseComment courseComment) {
        commentDao.createSelectivity(courseComment);
    }

    public CourseComment getById(Long refId) {
        return commentDao.getById(refId);
    }

    public void delete(CourseComment entity) {
        commentDao.delete(entity);
    }
}
