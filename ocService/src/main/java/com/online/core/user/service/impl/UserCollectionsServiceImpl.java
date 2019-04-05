package com.online.core.user.service.impl;

import com.online.common.page.TailPage;
import com.online.core.user.dao.UserCollectionsDao;
import com.online.core.user.domain.UserCollections;
import com.online.core.user.service.IUserCollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCollectionsServiceImpl implements IUserCollectionsService {
    @Autowired
    private UserCollectionsDao userCollectionsDao;

    public List<UserCollections> queryAll(UserCollections userCollections) {
        return userCollectionsDao.queryAll(userCollections);
    }

    public void delete(UserCollections userCollections) {
        userCollectionsDao.delete(userCollections);
    }

    public void createSelectivity(UserCollections userCollections) {
        userCollectionsDao.createSelectivity(userCollections);
    }

    public TailPage<UserCollections> queryPage(UserCollections queryEntity, TailPage<UserCollections> page) {
        Integer itemTotalCount = userCollectionsDao.getTotalCount(queryEntity);
        List<UserCollections> items = userCollectionsDao.queryPage(queryEntity,page);
        page.setItemsTotalCount(itemTotalCount);
        page.setItems(items);
        return page;
    }
}
