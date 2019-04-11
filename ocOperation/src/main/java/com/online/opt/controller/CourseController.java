package com.online.opt.controller;

import com.online.common.page.TailPage;
import com.online.common.storage.QiniuStorage;
import com.online.common.util.CalendarUtil;
import com.online.common.web.JsonView;
import com.online.core.auth.domain.AuthUser;
import com.online.core.auth.service.IAuthUserService;
import com.online.core.consts.domain.ConstsClassify;
import com.online.core.consts.service.IConstsClassifyService;
import com.online.core.course.domain.Course;
import com.online.core.course.service.ICourseService;
import com.online.core.statics.domain.CourseStudyStaticsDto;
import com.online.core.statics.domain.StaticsVO;
import com.online.core.statics.service.IStaticsService;
import com.online.opt.business.IPortalBusiness;
import com.online.opt.vo.ConstsClassifyVO;
import com.online.opt.vo.CourseSectionVO;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 课程管理
 */
@RequestMapping("/course")
@Controller
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @Autowired
    private IPortalBusiness portalBusiness;

    @Autowired
    private IStaticsService staticsService;

    @Autowired
    private IAuthUserService authUserService;

    @Autowired
    private IConstsClassifyService constsClassifyService;

    /**
     * 课程管理
     */
    @RequestMapping("/pagelist")
    public ModelAndView list(Course queryEntity, TailPage<Course> page){
        ModelAndView mv = new ModelAndView("cms/course/pagelist");

        if(StringUtils.isNotEmpty(queryEntity.getName())){
            queryEntity.setName(queryEntity.getName().trim());
        }else{
            queryEntity.setName(null);
        }

        page.setPageSize(5);
        page = courseService.queryPage(queryEntity, page);
        mv.addObject("page", page);
        mv.addObject("queryEntity", queryEntity);
        mv.addObject("curNav", "course");
        return mv;
    }

    /**
     * 课程上下架
     */
    @RequestMapping("/doSale")
    @ResponseBody
    public String doSale(Course entity){
        courseService.updateSelectivity(entity);
        //更新课程总时长

        return new JsonView().toString();
    }

    /**
     * 课程删除
     */
    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(Course entity){
        courseService.delete(entity);
        return new JsonView().toString();
    }

    /**
     * 根据id获取
     */
    @RequestMapping("/getById")
    @ResponseBody
    public String getById(Long id){
        Course course = courseService.getById(id);
        if (course.getPicture() != null){
            course.setPicture(QiniuStorage.getUrl(course.getPicture()));
        }
        return JsonView.render(course);
    }

    /**
     * 课程详情
     * @param id
     * @return
     */
    @RequestMapping("/read")
    public ModelAndView courseRead(Long id){
        String key = null;
        Course course = courseService.getById(id);
        if (null == course){
            return new ModelAndView("error/404");
        }
        if (StringUtils.isNotEmpty(course.getPicture())){
            key = QiniuStorage.getUrl(course.getPicture());
            course.setPicture(key);
        }
        ModelAndView mv = new ModelAndView("cms/course/read");
        mv.addObject("curNav","course");
        mv.addObject("course",course);

        //课程章节
        List<CourseSectionVO> chaptSections = this.portalBusiness.queryCourseSection(id);
        mv.addObject("chaptSections", chaptSections);

        //课程分类
        Map<String, ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();
        //所有一级分类
        List<ConstsClassifyVO> classifysList = new ArrayList<>();
        for (ConstsClassifyVO vo:
             classifyMap.values()) {
            classifysList.add(vo);
        }
        mv.addObject("classifys",classifysList);

        List<ConstsClassify> subClassifys = new ArrayList<>();
        for (ConstsClassifyVO vo:
             classifyMap.values()) {
            subClassifys.addAll(vo.getSubClassifyList());
        }
        mv.addObject("subClassifys",subClassifys);      //所有二级分类

        //获取报表统计信息
        CourseStudyStaticsDto staticsDto = new CourseStudyStaticsDto();
        staticsDto.setCourseId(course.getId());
        staticsDto.setEndDate(new Date());
        staticsDto.setStartDate(CalendarUtil.getPreNDay(new Date(),7));

        StaticsVO staticsVo = staticsService.queryCourseStudyStatistics(staticsDto);
        if (null != staticsVo){
            try {
                JSONObject json = JSONObject.fromObject(staticsVo);
                mv.addObject("staticsVo",staticsVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mv;
    }
    /**
     * 添加，修改课程
     * @param course
     * @param pictureImg
     * @return
     */
    @RequestMapping("/doMerge")
    @ResponseBody
    public String doMerge(Course course, @RequestParam MultipartFile pictureImg){
        String key = null;
        try {
            if (null != pictureImg && pictureImg.getBytes().length >0){
                key = QiniuStorage.uploadImage(pictureImg.getBytes());
                course.setPicture(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //判断教师
        if (StringUtils.isNotEmpty(course.getUsername())){
            AuthUser user = authUserService.getByUsername(course.getUsername());
            if (null == user){
                return JsonView.render(1).toString();
            }
        }else {
            return JsonView.render(1).toString();
        }

        if (null != course.getId()){
            courseService.updateSelectivity(course);
        }else {
            //判断获取分类
            if (StringUtils.isNotEmpty(course.getClassify())){
                ConstsClassify classify = constsClassifyService.getByCode(course.getClassify());
                if (null != classify){
                    course.setClassifyName(classify.getName());
                }
            }
            if (StringUtils.isNotEmpty(course.getSubClassify())){
                ConstsClassify subClassify = constsClassifyService.getByCode(course.getSubClassify());
                if (null != subClassify){
                    course.setSubClassifyName(subClassify.getName());
                }
            }
            courseService.createSelectivity(course);
        }
        return JsonView.render(course).toString();
    }

    /**
     * 进入添加课程
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView("cms/course/add");
        mv.addObject("curNav", "course");
        Map<String,ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();
        //所有一级分类
        List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
        for(ConstsClassifyVO vo : classifyMap.values()){
            classifysList.add(vo);
        }
        mv.addObject("classifys", classifysList);

        List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
        for(ConstsClassifyVO vo : classifyMap.values()){
            subClassifys.addAll(vo.getSubClassifyList());
        }
        mv.addObject("subClassifys", subClassifys);//所有二级分类
        return mv;
    }


    /**
     * 添加章节
     * @param courseId
     * @return
     */
    @RequestMapping("/append")
    public ModelAndView append(Long courseId){
        Course course = courseService.getById(courseId);
        if (null == course){
            return  new ModelAndView("error/404");
        }
        if (course.getPicture() != null){
            String url = QiniuStorage.getUrl(course.getPicture());
            course.setPicture(url);
        }
        ModelAndView mv = new ModelAndView("cms/course/append");
        mv.addObject("curNav", "course");
        mv.addObject("course", course);

        List<CourseSectionVO> chaptSections = this.portalBusiness.queryCourseSection(courseId);
        mv.addObject("chaptSections", chaptSections);
        return mv;
    }

}
