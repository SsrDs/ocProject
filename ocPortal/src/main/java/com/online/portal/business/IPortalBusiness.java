package com.online.portal.business;


import com.online.portal.vo.ConstsClassifyVO;

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
}
