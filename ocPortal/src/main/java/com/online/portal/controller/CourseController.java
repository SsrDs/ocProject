package com.online.portal.controller;

import com.online.common.storage.QiniuStorage;
import com.online.common.web.JsonView;
import com.online.common.web.SessionContext;
import com.online.core.auth.domain.AuthUser;
import com.online.core.auth.service.IAuthUserService;
import com.online.core.course.domain.Course;
import com.online.core.course.domain.CourseQueryDto;
import com.online.core.course.domain.CourseSection;
import com.online.core.course.service.ICourseSectionService;
import com.online.core.course.service.ICourseService;
import com.online.core.user.domain.UserCourseSection;
import com.online.core.user.service.IUserCourseSectionService;
import com.online.portal.business.ICourseBusiness;
import com.online.portal.vo.CourseSectionVO;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICourseBusiness courseBusiness;

    @Autowired
    private IAuthUserService authUserService;

    @Autowired
    private IUserCourseSectionService userCourseSectionService;

    @Autowired
    private ICourseSectionService courseSectionService;

    @RequestMapping("/learn/{courseId}")
    public ModelAndView learn(@PathVariable(value = "courseId") Long courseId){
        if (courseId == null){
            return new ModelAndView("error/404");
        }
        Course course = courseService.getById(courseId);
        if (course == null){
            return new ModelAndView("error/404");
        }
        //获取课程章节
        ModelAndView mv = new ModelAndView("learn");
        List<CourseSectionVO> sectionVOList = courseBusiness.queryCourseSection(courseId);
        mv.addObject("course",course);
        mv.addObject("sectionVOList",sectionVOList);

        //获取讲师
        AuthUser courseTeacher = authUserService.getByUsername(course.getUsername());
        if (null != courseTeacher && StringUtils.isNotEmpty(courseTeacher.getHeader())){
            courseTeacher.setHeader(QiniuStorage.getUrl(courseTeacher.getHeader()));
        }
        mv.addObject("courseTeacher",courseTeacher);

        //获取推荐课程
        CourseQueryDto queryEntity = new CourseQueryDto();
        queryEntity.descSortField("weight");
        queryEntity.setCount(5);    //5门推荐课
        queryEntity.setSubClassify(course.getSubClassify());
        List<Course> courseList = courseService.queryList(queryEntity);
        mv.addObject("courseList",courseList);

        //当前学习的章节
        UserCourseSection userCourseSection = new UserCourseSection();
        userCourseSection.setCourseId(courseId);
        userCourseSection.setUserId(SessionContext.getUserId());
        userCourseSection = userCourseSectionService.queryLatest(userCourseSection);
        if(null != userCourseSection){
            CourseSection curCourseSection = courseSectionService.getById(userCourseSection.getSectionId());
            mv.addObject("curCourseSection",curCourseSection);
        }else {
            CourseSection courseSection = new CourseSection();
            mv.addObject("curCourseSection",courseSection);
        }
        return mv;
    }

//    @RequestMapping("/section/{courseId}")
//    public ModelAndView section(@PathVariable Long courseId){
//        if (courseId == null){
//            return new ModelAndView("error/404");
//        }
//        Course course = courseService.getById(courseId);
//        if (course == null){
//            return new ModelAndView("error/404");
//        }
//        //获取课程章节
//        ModelAndView mv = new ModelAndView("common/section");
//        List<CourseSectionVO> sectionVOList = courseBusiness.queryCourseSection(courseId);
//        mv.addObject("course",course);
//        mv.addObject("sectionVOList",sectionVOList);
//
//        return mv;
//    }

    /**
     * 视频学习页面
     * @param sectionId
     * @return
     */
    @RequestMapping("/video/{sectionId}")
    public ModelAndView video(@PathVariable Long sectionId){
        if(null == sectionId){
            return new ModelAndView("error/404");
        }

        CourseSection courseSection = courseSectionService.getById(sectionId);
        if (courseSection == null){
            return new ModelAndView("error/404");
        }

        //获取课程章节
        ModelAndView mv = new ModelAndView("video");
        List<CourseSectionVO> sectionVOList = courseBusiness.queryCourseSection(courseSection.getCourseId());
        mv.addObject("courseSection",courseSection);
        mv.addObject("sectionVOList",sectionVOList);

        //学习记录
        UserCourseSection userCourseSection = new UserCourseSection();
        userCourseSection.setUserId(SessionContext.getUserId());
        userCourseSection.setCourseId(courseSection.getCourseId());
        userCourseSection.setSectionId(sectionId);
        UserCourseSection result = userCourseSectionService.queryLatest(userCourseSection);
        if (null == result){        //没有学习记录
            userCourseSection.setCreateTime(new Date());
            userCourseSection.setCreateUser(SessionContext.getUsername());
            userCourseSection.setUpdateTime(new Date());
            userCourseSection.setUpdateUser(SessionContext.getUsername());
        }else {
            result.setUpdateTime(new Date());
            userCourseSectionService.update(result);
        }
        return mv;
    }

    @RequestMapping(value = "/getCurLeanInfo")
    @ResponseBody
    public String getCurLeanInfo(){
        JsonView jv = new JsonView();
        //加载当前用户学习最新课程
        if(SessionContext.isLogin()){
            UserCourseSection userCourseSection = new UserCourseSection();
            userCourseSection.setUserId(SessionContext.getUserId());
            userCourseSection = this.userCourseSectionService.queryLatest(userCourseSection);
            if(null != userCourseSection){
                JSONObject jsObj = new JSONObject();
                CourseSection curCourseSection = this.courseSectionService.getById(userCourseSection.getSectionId());
                jsObj.put("curCourseSection", curCourseSection);
                Course curCourse = courseService.getById(userCourseSection.getCourseId());
                jsObj.put("curCourse", curCourse);
                jv.setData(jsObj);
            }
        }
        return jv.toString();
    }
}
