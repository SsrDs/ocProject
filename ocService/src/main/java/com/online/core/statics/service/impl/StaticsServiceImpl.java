package com.online.core.statics.service.impl;

import com.online.core.statics.dao.CourseStudyStaticsDao;
import com.online.core.statics.domain.CourseStudyStaticsDto;
import com.online.core.statics.domain.StaticsVO;
import com.online.core.statics.service.IStaticsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaticsServiceImpl implements IStaticsService {
    @Autowired
    private CourseStudyStaticsDao courseStudyStaticsDao;

    public StaticsVO queryCourseStudyStatistics(CourseStudyStaticsDto staticsDto) {
        List<CourseStudyStaticsDto> list = courseStudyStaticsDao.queryCourseStudyStatistics(staticsDto);

        StaticsVO returnVo = new StaticsVO();
        List<String> categories = new ArrayList<String>();
        List<Integer> data = new ArrayList<Integer>();

        if (CollectionUtils.isNotEmpty(list)){
            for (CourseStudyStaticsDto item:
                 list) {
                categories.add(item.getDateStr());
                data.add(item.getTotalCount());
            }
            returnVo.setCategories(categories);
            returnVo.setData(data);
        }
        return returnVo;
    }
}
