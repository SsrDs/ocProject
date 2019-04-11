package com.online.opt.controller;

import com.online.common.page.TailPage;
import com.online.common.web.JsonView;
import com.online.core.auth.domain.AuthUser;
import com.online.core.auth.service.IAuthUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/user")
public class AuthUserController {

    @Autowired
    private IAuthUserService authUserService;

    @RequestMapping("/doMerge")
    @ResponseBody
    public String doMerge(AuthUser authUser){
        authUser.setUsername(null);
        authUser.setRealname(null);
        authUserService.updateSelectivity(authUser);

        return new JsonView(0).toString();
    }

    @RequestMapping("/getById")
    @ResponseBody
    public String getById(Long id){
        AuthUser user = authUserService.getById(id);
        return JsonView.render(user);
    }

    /**
     * 分页获取
     * @param queryEntity
     * @param page
     * @return
     */
    @RequestMapping("/userPageList")
    public ModelAndView userPageList(AuthUser queryEntity, TailPage<AuthUser> page){
        ModelAndView mv = new ModelAndView("cms/user/userPageList");
        mv.addObject("curNav","user");

        if (StringUtils.isNotEmpty(queryEntity.getUsername())){
            queryEntity.setUsername(queryEntity.getUsername().trim());
        }else {
            queryEntity.setUsername(null);
        }
        if (Integer.valueOf(-1).equals(queryEntity.getStatus())){
            queryEntity.setStatus(null);
        }
        page =  authUserService.queryPage(queryEntity,page);
        mv.addObject("page",page);
        mv.addObject("queryEntity",queryEntity);

        return mv;
    }
}
