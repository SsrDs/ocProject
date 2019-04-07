package com.online.core.consts.service.impl;

import com.online.core.consts.dao.ConstsClassifyDao;
import com.online.core.consts.domain.ConstsClassify;
import com.online.core.consts.service.IConstsClassifyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstsClassifyServiceImpl implements IConstsClassifyService {
    @Autowired
    private ConstsClassifyDao classifyDao;

    public List<ConstsClassify> queryAll() {
        return classifyDao.queryAll() ;
    }

    public ConstsClassify getByCode(String c) {
        if (StringUtils.isNotEmpty(c)){
            ConstsClassify constsClassify = classifyDao.getByCode(c);
            return constsClassify;
        }else {
            return null;
        }
    }

    public ConstsClassify getById(Long id) {
        return classifyDao.getById(id);
    }

    public void createSelectivity(ConstsClassify constsClassify) {
        classifyDao.createSelectivity(constsClassify);
    }

    public void updateSelectivity(ConstsClassify constsClassify) {
        classifyDao.updateSelectivity(constsClassify);
    }

    public void deleteLogic(Long id) {
        classifyDao.deleteLogic(id);
    }

}
