package com.online.portal.controller;


import com.online.core.auth.domain.AuthUser;
import com.online.core.auth.service.IAuthUserService;
import com.online.core.consts.CourseEnum;
import com.online.core.consts.domain.ConstsSiteCarousel;
import com.online.core.consts.service.ISiteCarouselService;
import com.online.core.course.domain.Course;
import com.online.core.course.domain.CourseQueryDto;
import com.online.core.course.service.ICourseService;
import com.online.core.user.domain.User;
import com.online.core.user.service.IUserTest;
import com.online.portal.business.IPortalBusiness;
import com.online.portal.vo.ConstsClassifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping()
public class PortalController {
    @Autowired
    private IUserTest userTest;

    @Autowired
    private ISiteCarouselService siteCarouselService;

    @Autowired
    private IPortalBusiness portalBusiness;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IAuthUserService authUserService;

    /**
     * 进入主页
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");

        //加载轮播图片
        List<ConstsSiteCarousel> carouselList = siteCarouselService.queryCarousels(4);
        mv.addObject("carouselList",carouselList);

        //课程分类(一级分类)
        List<ConstsClassifyVO> classifies = portalBusiness.queryAllClassify();

        //课程推荐
        portalBusiness.prepareRecomdCourses(classifies);
        mv.addObject("classifies",classifies);

        //获取5门实战课程推荐，根据（weight）进行排序
        CourseQueryDto queryDto = new CourseQueryDto();
        queryDto.setCount(5);   //5门
        queryDto.setFree(CourseEnum.FREE_NOT.value());  //非免费的课： 实战课
        queryDto.descSortField("weight");   //按照weight排序
        List<Course> actionCourseList = courseService.queryList(queryDto);
//        for (Course c:
//             actionCourseList) {
//            System.out.println(c.getName()+":\n"+c.getPicture());
//        }
        mv.addObject("actionCourseList",actionCourseList);

        //获取5门免费课推荐，根据（weight）进行排序
        queryDto.setFree(CourseEnum.FREE.value());      //免费的课
        List<Course> freeCourseList = courseService.queryList(queryDto);
        mv.addObject("freeCourseList",freeCourseList);

        //获取7门java课程，根据（学习数量studyCount）进行排序
        queryDto.setCount(7);
        queryDto.setFree(null);
        queryDto.setSubClassify("java");
        queryDto.descSortField("studyCount");   //按照studyCount降序排列
        List<Course> javaCourseList = courseService.queryList(queryDto);
        mv.addObject("javaCourseList",javaCourseList);

        //获取名校讲师
        List<AuthUser> authUserList = authUserService.selectTeacher();
        mv.addObject("authUserList",authUserList);

        return mv;
    }

    /**
     * 测试代码
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/test")
    public ModelAndView register(@RequestParam("username") String username, @RequestParam("password") String password){
        System.out.println("111111111111"+username);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userTest.register(user);
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
