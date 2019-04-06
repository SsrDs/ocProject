package com.online.core.consts.service.impl;

import com.online.common.page.TailPage;
import com.online.core.consts.dao.ConstsCollegeDao;
import com.online.core.consts.domain.ConstsCollege;
import com.online.core.consts.service.IConstsCollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstsCollegeServiceImpl implements IConstsCollegeService {
    @Autowired
    private ConstsCollegeDao constsCollegeDao;

    public TailPage<ConstsCollege> queryPage(ConstsCollege queryEntity, TailPage<ConstsCollege> page) {
        Integer itemsTotalCount = constsCollegeDao.getTotalItemsCount(queryEntity);
        List<ConstsCollege> items = constsCollegeDao.queryPage(queryEntity,page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    public ConstsCollege getById(Long id) {
        return constsCollegeDao.getById(id);
    }

    public void createSelectivity(ConstsCollege constsCollege) {
        constsCollegeDao.createSelectivity(constsCollege);
    }

    public void updateSelectivity(ConstsCollege constsCollege) {
        constsCollegeDao.updateSelectivity(constsCollege);
    }

    public ConstsCollege getByCode(String code) {
        return constsCollegeDao.getByCode(code);
    }

    public void deleteLogic(ConstsCollege constsCollege) {
        constsCollegeDao.deleteLogic(constsCollege);
    }
}
