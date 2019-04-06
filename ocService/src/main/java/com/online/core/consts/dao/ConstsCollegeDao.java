package com.online.core.consts.dao;

import com.online.common.page.TailPage;
import com.online.core.consts.domain.ConstsCollege;

import java.util.List;

public interface ConstsCollegeDao {

    /**
     * 获取总记录数
     * @param queryEntity
     * @return
     */
    Integer getTotalItemsCount(ConstsCollege queryEntity);

    /**
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    List<ConstsCollege> queryPage(ConstsCollege queryEntity, TailPage<ConstsCollege> page);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    ConstsCollege getById(Long id);

    /**
     * 根据code获取
     * @param code
     * @return
     */
    ConstsCollege getByCode(String code);

    /**
     * 添加信息
     * @param constsCollege
     */
    void createSelectivity(ConstsCollege constsCollege);

    /**
     * 修改信息
     * @param constsCollege
     */
    void updateSelectivity(ConstsCollege constsCollege);

    /**
     * 逻辑删除
     * @param constsCollege
     */
    void deleteLogic(ConstsCollege constsCollege);
}
