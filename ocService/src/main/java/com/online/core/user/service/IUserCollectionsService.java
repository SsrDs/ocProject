package com.online.core.user.service;

import com.online.common.page.TailPage;
import com.online.core.user.domain.UserCollections;

import java.util.List;

public interface IUserCollectionsService {

    /**
     * 获取所有
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
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    TailPage<UserCollections> queryPage(UserCollections queryEntity, TailPage<UserCollections> page);
}
