package com.online.core.course.service.impl;

import com.online.core.course.dao.CourseSectionDao;
import com.online.core.course.domain.CourseSection;
import com.online.core.course.service.ICourseSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSectionServiceImpl implements ICourseSectionService {
    @Autowired
    private CourseSectionDao sectionDao;

    public List<CourseSection> queryAll(CourseSection queryEntity) {
        return sectionDao.queryAll(queryEntity);
    }

    public CourseSection getById(Long sectionId) {
        return sectionDao.getById(sectionId);
    }

    public Integer getMaxSort(Long courseId) {
        return sectionDao.getMaxSort(courseId);
    }

    public void createSelectivity(CourseSection courseSection) {
        sectionDao.createSelectivity(courseSection);
    }

    public void createList(List<CourseSection> subCourseSections) {
        sectionDao.createList(subCourseSections);
    }

    public void updateSelectivity(CourseSection courseSection) {
        sectionDao.updateSelectivity(courseSection);
    }

    public void delete(CourseSection entity) {
        sectionDao.delete(entity);
    }
}
