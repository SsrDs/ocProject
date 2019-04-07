package com.online.core.consts.service;

import com.online.core.consts.domain.ConstsClassify;

import java.util.List;

public interface IConstsClassifyService {
    /**
     * 获取所有
     * @return
     */
    List<ConstsClassify> queryAll();

    /**
     * 根据code查询课程
     * @param c
     * @return
     */
    ConstsClassify getByCode(String c);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    ConstsClassify getById(Long id);

    /**
     * 添加
     * @param constsClassify
     */
    void createSelectivity(ConstsClassify constsClassify);

    /**
     * 修改
     * @param constsClassify
     */
    void updateSelectivity(ConstsClassify constsClassify);

    /**
     * 逻辑删除
     * @param id
     */
    void deleteLogic(Long id);
}
