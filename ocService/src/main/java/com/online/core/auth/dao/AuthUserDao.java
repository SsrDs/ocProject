package com.online.core.auth.dao;

import com.online.core.auth.domain.AuthUser;

import java.util.List;

public interface AuthUserDao {
    /**
     * 根据usernmae获取
     * @param username
     * @return
     */
    AuthUser getByUsername(String username);

    /**
     *创建新用户
     * @param authUser
     */
    void createSelectivity(AuthUser authUser);

    /**
     * 获取讲师名单
     * @return
     */
    List<AuthUser> selectTeacher();

    /**
     * 根据username跟password获取
     * @param authUser
     * @return
     */
    AuthUser getByUsernameAndPassword(AuthUser authUser);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    AuthUser getById(Long id);

    /**
     * 修改用户
     * @param authUser
     */
    void updateSelectivity(AuthUser authUser);
}
