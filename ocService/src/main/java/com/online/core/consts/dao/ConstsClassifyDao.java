package com.online.core.consts.dao;

import com.online.core.consts.domain.ConstsClassify;

import java.util.List;

public interface ConstsClassifyDao {
    /**
     * 获取所有
     * @return
     */
    List<ConstsClassify> queryAll();

    /**
     * 根据code值获取
     * @param code
     * @return
     */
    ConstsClassify getByCode(String code);
}
