package com.online.core.user.service.impl;

import com.online.core.user.dao.UserTestDao;
import com.online.core.user.domain.User;
import com.online.core.user.service.IUserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserTestImpl implements IUserTest {
    @Resource
    private UserTestDao userMapper;

    public void register(User user) {
        System.out.println(user.getUsername() + "\n" + user.getPassword());
        userMapper.login(user);
        System.out.println("1111122222");
    }
}
