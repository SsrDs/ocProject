package com.online.core.user.service;

import com.online.common.page.TailPage;
import com.online.core.user.domain.UserFollowStudyRecord;
import com.online.core.user.domain.UserFollows;

import java.util.List;

public interface IUserFollowService {
    /**
     * 获取所有
     * @param userFollows
     * @return
     */
    List<UserFollows> queryAll(UserFollows userFollows);

    /**
     * 物理删除
     * @param userFollows
     */
    void delete(UserFollows userFollows);

    /**
     * 添加
     * @param userFollows
     */
    void createSelectivity(UserFollows userFollows);

    /**
     * 根据登录id查询
     * @param queryEntity
     * @param page
     * @return
     */
    TailPage<UserFollowStudyRecord> queryUserFollowStudyRecordPage(UserFollowStudyRecord queryEntity, TailPage<UserFollowStudyRecord> page);
}
