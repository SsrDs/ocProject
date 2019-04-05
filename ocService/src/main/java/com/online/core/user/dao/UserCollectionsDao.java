package com.online.core.user.dao;

import com.online.common.page.TailPage;
import com.online.core.user.domain.UserCollections;

import java.util.List;

public interface UserCollectionsDao {
    /**
     * 查询所有
     * @param userCollections
     * @return
     */
     List<UserCollections> queryAll(UserCollections userCollections);

    /**
     * 物理删除
     * @param userCollections
     */
    void delete(UserCollections userCollections);

    /**
     * 创建
     * @param userCollections
     */
    void createSelectivity(UserCollections userCollections);

    /**
     * 获取总数
     * @param queryEntity
     * @return
     */
    Integer getTotalCount(UserCollections queryEntity);

    /**
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    List<UserCollections> queryPage(UserCollections queryEntity, TailPage<UserCollections> page);
}
