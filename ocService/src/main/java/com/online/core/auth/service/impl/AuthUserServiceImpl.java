package com.online.core.auth.service.impl;

import com.online.common.page.TailPage;
import com.online.common.storage.QiniuStorage;
import com.online.core.auth.dao.AuthUserDao;
import com.online.core.auth.domain.AuthUser;
import com.online.core.auth.service.IAuthUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthUserServiceImpl implements IAuthUserService {

    @Autowired
    private AuthUserDao authUserDao;
    /**
     * 根据username获取
     */
    public AuthUser getByUsername(String username){
//        System.out.println("2222");
//        System.out.println(authUserDao);
        return authUserDao.getByUsername(username);
    }

    /**
     * 保存用户
     * @param authUser
     */
    public void createSelectivity(AuthUser authUser) {
        authUserDao.createSelectivity(authUser);
    }

    /**
     * 获取首页推荐讲师
     * @return
     */
    public List<AuthUser> selectTeacher() {
        List<AuthUser> authUserList = authUserDao.selectTeacher();
        if (CollectionUtils.isNotEmpty(authUserList)){
            for (AuthUser item:
                 authUserList) {
                if (StringUtils.isNotEmpty(item.getHeader())){
                    item.setHeader(QiniuStorage.getUrl(item.getHeader()));
                }
            }
        }
        return authUserList;
    }

    public AuthUser getByUsernameAndPassword(AuthUser authUser) {
        return authUserDao.getByUsernameAndPassword(authUser);
    }

    public AuthUser getById(Long id) {
        return authUserDao.getById(id);
    }

    public void updateSelectivity(AuthUser authUser) {
        authUserDao.updateSelectivity(authUser);
    }

    public TailPage<AuthUser> queryPage(AuthUser queryEntity, TailPage<AuthUser> page) {
        Integer itemsTatiaCount = authUserDao.getItemsTatilCount(queryEntity);
        List<AuthUser> items = authUserDao.queryPage(queryEntity,page);
        page.setItemsTotalCount(itemsTatiaCount);
        page.setItems(items);
        return page;
    }

}
