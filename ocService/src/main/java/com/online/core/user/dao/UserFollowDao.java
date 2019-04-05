package com.online.core.user.dao;

import com.online.common.page.TailPage;
import com.online.core.user.domain.UserFollowStudyRecord;
import com.online.core.user.domain.UserFollows;

import java.util.List;

public interface UserFollowDao {

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
     * 查询总记录数
     * @param queryEntity
     * @return
     */
    Integer getFollowStudyRecordCount(UserFollowStudyRecord queryEntity);

    /**
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    List<UserFollowStudyRecord> queryFollowStudyRecord(UserFollowStudyRecord queryEntity, TailPage<UserFollowStudyRecord> page);
}
