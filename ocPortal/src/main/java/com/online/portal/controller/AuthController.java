package com.online.portal.controller;

import com.online.common.util.EncryptUtil;
import com.online.common.web.JsonView;
import com.online.common.web.SessionContext;
import com.online.core.auth.domain.AuthUser;
import com.online.core.auth.service.IAuthUserService;
import com.online.web.EmailUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private IAuthUserService authUserService;

    /**
     * 注册页面
     */
    @RequestMapping(value = "/register")
    public ModelAndView register(){
        if (SessionContext.isLogin())
            return new ModelAndView("redirect:/index.html");
        return new ModelAndView("auth/register");
    }

    /**
     * 获取验证码
     * @param mail
     * @param response
     * @param request
     * @throws IOException
     * @throws AddressException
     * @throws MessagingException
     */
    @RequestMapping("/doEm")
    public void doEm(String mail, HttpServletResponse response, HttpServletRequest request) throws IOException, AddressException, MessagingException {
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("utf-8");
        int idcode = (int) (Math.random()*100000);
        String text = Integer.toString(idcode);
        request.getSession().setAttribute("idcode", text);
        EmailUtil emailUtil = new EmailUtil();
        String code = emailUtil.toEmail(mail,request);
        System.out.println(code);
        out.write("success");
    }

    /**
     * 实现注册
     */
    @RequestMapping(value = "/doRegister")
    @ResponseBody
    public String doRegister(AuthUser authUser, String identiryCode, HttpServletRequest request){
        //邮箱验证码判断
        if (null != identiryCode && !identiryCode.equalsIgnoreCase(request.getSession().getAttribute("code").toString())){
            return JsonView.render(2);
        }
        AuthUser authUser1 =authUserService.getByUsername(authUser.getUsername());
        if (authUser1 != null){
            return JsonView.render(1);
        }else {
            authUser.setPassword(EncryptUtil.encodedByMD5(authUser.getPassword()));
            authUserService.createSelectivity(authUser);
            return JsonView.render(0);
        }
    }

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("auth/login");
    }

    /**
     * ajax登录
     * @param authUser
     * @param identiryCode
     * @param rememberMe
     * @param request
     * @return
     */
    @RequestMapping("/ajaxLogin")
    @ResponseBody
    public String ajaxLogin(AuthUser authUser,String identiryCode,Integer rememberMe,HttpServletRequest request){
        //验证码判断
        if (null != identiryCode && !identiryCode.equalsIgnoreCase(SessionContext.getIdentifyCode(request))){
            return JsonView.render(2,"验证码不正确");
        }
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(authUser.getUsername(),EncryptUtil.encodedByMD5(authUser.getPassword()));
        try {
            if (rememberMe != null && rememberMe == 1){
                token.setRememberMe(true);
            }
            currentUser.login(token);       //shiro:不抛出异常，登录成功
            return new JsonView().toString();
        } catch (AuthenticationException e) {   //登录失败
            return JsonView.render(1,"用户名或密码不正确");
        }
    }

    @RequestMapping(value = "/doLogin")
    public ModelAndView doLogin(AuthUser user, Integer  rememberMe, String identiryCode, HttpServletRequest request, HttpServletResponse response){
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
            return new ModelAndView("redirect:/user/home.html");
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

}
