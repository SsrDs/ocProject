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
}
