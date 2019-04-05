package com.online.core.course.service.impl;

import com.online.common.page.TailPage;
import com.online.common.storage.QiniuStorage;
import com.online.core.consts.CourseEnum;
import com.online.core.course.dao.CourseDao;
import com.online.core.course.domain.Course;
import com.online.core.course.domain.CourseQueryDto;
import com.online.core.course.service.ICourseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private CourseDao courseDao;

    private void prepareCoursePicture(Course course){
        if(null != course && StringUtils.isNotEmpty(course.getPicture())){
            course.setPicture(QiniuStorage.getUrl(course.getPicture()));
        }
    }

    public List<Course> queryList(CourseQueryDto queryDto) {
        if (null == queryDto.getOnsale()){
            queryDto.setOnsale(CourseEnum.ONSALE.value());
        }
        List<Course> list = courseDao.queryList(queryDto);
        for (Course c:list) {
            if (StringUtils.isNotEmpty(c.getPicture())){    //有图片
                c.setPicture(QiniuStorage.getUrl(c.getPicture()));
            }
            else c.setPicture(null);
        }
        return list;
    }

    public TailPage<Course> queryPage(Course queryEntity, TailPage<Course> page) {
        Integer itemsTotalCount = courseDao.getTotalItemsCount(queryEntity);
        List<Course> items = courseDao.queryPage(queryEntity,page);
        if (CollectionUtils.isNotEmpty(items)){
            for (Course item:
                 items) {
                prepareCoursePicture(item);
            }
        }
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    public Course getById(Long courseId) {
        return courseDao.getById(courseId);
    }
}
