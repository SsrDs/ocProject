package com.online.opt.controller;

import com.online.common.util.CalendarUtil;
import com.online.common.web.SessionContext;
import com.online.core.statics.domain.CourseStudyStaticsDto;
import com.online.core.statics.domain.StaticsVO;
import com.online.core.statics.service.IStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public ModelAndView index(HttpServletRequest request){
        if (SessionContext.isLogin()){
            ModelAndView mv = new ModelAndView("cms/index");
            mv.addObject("curNav","home");
            return mv;
        }else {
            return new ModelAndView("auth/login");
        }
    }
}
