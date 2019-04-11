package com.online.core.statics.dao;

import com.online.core.statics.domain.CourseStudyStaticsDto;

import java.util.List;

public interface CourseStudyStaticsDao {
    /**
     * 统计课程学习情况
     * @param staticsDto
     * @return
     */
    List<CourseStudyStaticsDto> queryCourseStudyStatistics(CourseStudyStaticsDto staticsDto);
}
