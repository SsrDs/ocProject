package com.online.portal.controller;


import com.online.common.page.TailPage;
import com.online.common.storage.QiniuStorage;
import com.online.common.web.JsonView;
import com.online.common.web.SessionContext;
import com.online.core.course.domain.CourseComment;
import com.online.core.course.domain.CourseSection;
import com.online.core.course.service.ICourseCommentService;
import com.online.core.course.service.ICourseSectionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 课程评论
 */
@Controller
@RequestMapping("/courseComment")
public class CourseCommentController {

    @Autowired
    private ICourseCommentService courseCommentService;

    @Autowired
    private ICourseSectionService courseSectionService;

    /**
     * 加载评论
     * @param queryEntity
     * @param page
     * @return
     */
    @RequestMapping("/segment")
    public ModelAndView segment(CourseComment queryEntity, TailPage<CourseComment> page){

            if (null == queryEntity.getCourseId() || queryEntity.getType() == null)
                return new ModelAndView("/error/404");
            ModelAndView mv = new ModelAndView("commentSegment");
            TailPage<CourseComment> commentTailPage = courseCommentService.queryPage(queryEntity,page);

            //处理头像
            for (CourseComment item :commentTailPage){
                if(StringUtils.isNotEmpty(item.getHeader())){
                    item.setHeader(QiniuStorage.getUrl(item.getHeader()));
                }
            }
            mv.addObject("page",commentTailPage);
            return mv;
    }

    /**
     * 发表评论
     * @param courseComment
     * @return
     */
    @RequestMapping("/doComment")
    @ResponseBody
    public String doComment(CourseComment courseComment, HttpServletRequest request,String indeityCode){
        //验证码判断
        if (null == indeityCode || !indeityCode.equalsIgnoreCase(SessionContext.getIdentifyCode(request))){
            return new JsonView(2).toString();      //验证码错误
        }
        if (courseComment.getContent().trim().length() > 200 || courseComment.getContent().trim().length() == 0){
            return new JsonView(3).toString();      //文字太长或者为空
        }

        if (null != courseComment.getRefId()){      //个人中心的答疑
           CourseComment refComment = courseCommentService.getById(courseComment.getRefId());
           if (null != refComment){
               CourseSection courseSection = courseSectionService.getById(refComment.getSectionId());
                courseComment.setRefContent(refComment.getContent());
                courseComment.setRefId(courseComment.getRefId());
                courseComment.setCourseId(refComment.getCourseId());
                courseComment.setSectionId(refComment.getSectionId());
                courseComment.setSectionTitle(courseSection.getName());
                courseComment.setToUsername(refComment.getToUsername());
                courseComment.setUsername(SessionContext.getUsername());
                courseComment.setCreateTime(new Date());
                courseComment.setCreateUser(SessionContext.getUsername());
                courseComment.setUpdateTime(new Date());
                courseComment.setUpdateUser(SessionContext.getUsername());

                courseCommentService.createSelectivity(courseComment);

                return new JsonView(0).toString();
           }
        }else {

            CourseSection courseSection = courseSectionService.getById(courseComment.getSectionId());   //找到是某节的评论
            if (null != courseSection) {
                courseComment.setSectionTitle(courseSection.getName());
                courseComment.setToUsername(SessionContext.getUsername());
                courseComment.setCreateTime(new Date());
                courseComment.setCreateUser(SessionContext.getUsername());
                courseComment.setUpdateTime(new Date());
                courseComment.setUpdateUser(SessionContext.getUsername());

                courseCommentService.createSelectivity(courseComment);

                return new JsonView(0).toString();
            }
        }
        return new JsonView(1).toString();
    }
}
