package com.online.opt.controller;

import com.online.common.util.EncryptUtil;
import com.online.common.web.JsonView;
import com.online.common.web.SessionContext;
import com.online.core.auth.domain.AuthUser;
import com.online.core.auth.service.IAuthUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private IAuthUserService authUserService;

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("auth/login");
    }


    /**
     * 登录
     * @param user
     * @param rememberMe
     * @param identiryCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/doLogin")
    public ModelAndView doLogin(AuthUser user, Integer  rememberMe, String identiryCode, HttpServletRequest request){
        //如果已经登录过
        if(SessionContext.getAuthUser() != null){
            return new ModelAndView("redirect:/user/home.html");
        }

        //验证码判断
        if(identiryCode!=null && !identiryCode.equalsIgnoreCase(SessionContext.getIdentifyCode(request))){
            ModelAndView mv = new ModelAndView("auth/login");
            mv.addObject("errcode", 1);
            return mv;
        }

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),EncryptUtil.encodedByMD5(user.getPassword()));
        try {
            if (rememberMe != null && rememberMe == 1){
                token.setRememberMe(true);
            }
            currentUser.login(token);       //shiro:不抛出异常，登录成功
            return new ModelAndView("redirect:/cms/index.html");
        } catch (AuthenticationException e) {   //登录失败
            ModelAndView mv =  new ModelAndView("/auth/login");
            mv.addObject("errcode",2);
            return mv;
        }
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        SessionContext.shiroLogout();
        return new ModelAndView("redirect:/index.html");
    }

    @Test
    public void f(){
        AuthUser tmpAuthUser = new AuthUser();
        tmpAuthUser.setUsername("wangyangming");
        tmpAuthUser.setPassword("7FA8282AD93047A4D6FE6111C93B308A");

        tmpAuthUser = authUserService.getByUsernameAndPassword(tmpAuthUser);
    }

}
