package com.online.opt.controller;

import com.online.common.web.SessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理
 */
@RequestMapping("/cms")
@Controller
public class CmsController {

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(){
        if (SessionContext.isLogin()){
            ModelAndView mv = new ModelAndView("cms/index");
            mv.addObject("curNav","home");
            return mv;
        }else {
            return new ModelAndView("auth/login");
        }
    }
}
