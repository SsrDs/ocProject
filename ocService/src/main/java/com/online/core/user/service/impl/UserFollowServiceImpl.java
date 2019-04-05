package com.online.core.user.service.impl;

import com.online.common.page.TailPage;
import com.online.core.user.dao.UserFollowDao;
import com.online.core.user.domain.UserFollowStudyRecord;
import com.online.core.user.domain.UserFollows;
import com.online.core.user.service.IUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserFollowServiceImpl implements IUserFollowService {
    @Autowired
    private UserFollowDao userFollowDao;

    public List<UserFollows> queryAll(UserFollows userFollows) {
        return userFollowDao.queryAll(userFollows);
    }

    public void delete(UserFollows userFollows) {
        userFollowDao.delete(userFollows);
    }

    public void createSelectivity(UserFollows userFollows) {
        userFollowDao.createSelectivity(userFollows);
    }

    public TailPage<UserFollowStudyRecord> queryUserFollowStudyRecordPage(UserFollowStudyRecord queryEntity, TailPage<UserFollowStudyRecord> page) {
        Integer itemsTotalCount = userFollowDao.getFollowStudyRecordCount(queryEntity);
        List<UserFollowStudyRecord> items = userFollowDao.queryFollowStudyRecord(queryEntity,page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }
}
