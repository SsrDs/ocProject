package com.online.opt.business;


import com.online.opt.vo.ConstsClassifyVO;
import com.online.opt.vo.CourseSectionVO;

import java.util.List;
import java.util.Map;

public interface IPortalBusiness {
    /**
     * 获取所有，包括一级分和二级分类
     * @return
     */
    List<ConstsClassifyVO> queryAllClassify();

    /**
     * 获取所有分类
     */
    Map<String,ConstsClassifyVO> queryAllClassifyMap();

    /**
     * 为分类设置课程推荐
     * @param classifyVOS
     */
    void prepareRecomdCourses(List<ConstsClassifyVO> classifyVOS);

    /**
     * 获取课程章节
     * @param id
     * @return
     */
    List<CourseSectionVO> queryCourseSection(Long id);
}
