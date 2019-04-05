package com.online.core.auth.service;

import com.online.core.auth.domain.AuthUser;

import java.util.List;

public interface IAuthUserService {

    /**
     * 查看用户名是否存在
     * @param username
     * @return
     */
    AuthUser getByUsername(String username);

    /**
     * 实现注册功能
     * @param authUser
     */
    void createSelectivity(AuthUser authUser);

    /**
     * 获取讲师
     * @return
     */
    List<AuthUser> selectTeacher();


    /**
     * 根据username和password获取
     * @param tmpAuthUser
     * @return
     */
    AuthUser getByUsernameAndPassword(AuthUser tmpAuthUser);

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
