package com.online.core.statics.service;

import com.online.core.statics.domain.CourseStudyStaticsDto;
import com.online.core.statics.domain.StaticsVO;

/**
 * 报表统计
 */
public interface IStaticsService {

    /**
     * 统计课程学习情况
     * @param staticsDto
     * @return
     */
    StaticsVO queryCourseStudyStatistics(CourseStudyStaticsDto staticsDto);
}
