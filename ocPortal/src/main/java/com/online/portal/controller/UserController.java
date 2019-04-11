package com.online.portal.controller;

import com.online.common.page.TailPage;
import com.online.common.storage.QiniuStorage;
import com.online.common.util.EncryptUtil;
import com.online.common.web.JsonView;
import com.online.common.web.SessionContext;
import com.online.core.auth.domain.AuthUser;
import com.online.core.auth.service.IAuthUserService;
import com.online.core.course.domain.CourseComment;
import com.online.core.course.service.ICourseCommentService;
import com.online.core.user.domain.UserCollections;
import com.online.core.user.domain.UserCourseSection;
import com.online.core.user.domain.UserCourseSectionDto;
import com.online.core.user.domain.UserFollowStudyRecord;
import com.online.core.user.service.IUserCollectionsService;
import com.online.core.user.service.IUserCourseSectionService;
import com.online.core.user.service.IUserFollowService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserFollowService userFollowService;

    @Autowired
    private IUserCourseSectionService userCourseSectionService;

    @Autowired
    private IUserCollectionsService userCollectionsService;

    @Autowired
    private IAuthUserService authUserService;

    @Autowired
    private ICourseCommentService courseCommentService;

    /**
     * 首页
     * @param page
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView index(TailPage<UserFollowStudyRecord> page){
        ModelAndView mv = new ModelAndView("user/home");
        mv.addObject("curNav","home");

        //加载关注用户的动态
        UserFollowStudyRecord queryEntity = new UserFollowStudyRecord();
        queryEntity.setUserId(SessionContext.getUserId());
        page.setPageSize(3);
        page = userFollowService.queryUserFollowStudyRecordPage(queryEntity,page);



        //处理用户头像
        for (UserFollowStudyRecord item :page.getItems()){
            if (StringUtils.isNotEmpty(item.getHeader())){
                item.setHeader(QiniuStorage.getUrl(item.getHeader()));
            }
        }
        mv.addObject("page",page);

        return mv;
    }

    /**
     * 我的课程
     * @param page
     * @return
     */
    @RequestMapping("/course")
    public ModelAndView course(TailPage<UserCourseSectionDto> page){
        ModelAndView mv = new ModelAndView("user/course");
        mv.addObject("curNav","course");

        UserCourseSection queryEntity = new UserCourseSection();
        queryEntity.setUserId(SessionContext.getUserId());
        page.setPageSize(3);
        page = userCourseSectionService.queryPage(queryEntity,page);
        mv.addObject("page",page);
        return mv;
    }

    @RequestMapping("/collect")
    public ModelAndView collect(TailPage<UserCollections> page){
        ModelAndView mv = new ModelAndView("user/collect");
        mv.addObject("curNav","collect");

        UserCollections queryEntity = new UserCollections();
        queryEntity.setUserId(SessionContext.getUserId());
        page.setPageSize(4);
        page = userCollectionsService.queryPage(queryEntity,page);

        mv.addObject("page",page);
        return mv;
    }

    /**
     * 个人信息
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(){
        ModelAndView mv = new ModelAndView("user/info");
        mv.addObject("curNav","info");

        AuthUser authUser = authUserService.getById(SessionContext.getUserId());
        if (authUser != null && StringUtils.isNotEmpty(authUser.getHeader())){
                authUser.setHeader(QiniuStorage.getUrl(authUser.getHeader()));
        }
        mv.addObject("authUser",authUser);
        return mv;
    }

    @RequestMapping("/saveInfo")
    @ResponseBody
    public String saveInfo(AuthUser authUser, @RequestParam MultipartFile pictureImg){
        String key = null;
        try {
            authUser.setId(SessionContext.getUserId());
            if (null != pictureImg && pictureImg.getBytes().length > 0){
                key = QiniuStorage.uploadImage(pictureImg.getBytes());
                authUser.setHeader(key);
            }
            authUserService.updateSelectivity(authUser);

            Subject subject = SecurityUtils.getSubject();
            AuthUser authUser1 = (AuthUser) subject.getPrincipal();
            //修改属性
            authUser1.setHeader(QiniuStorage.getUrl(key));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonView().toString();
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping("/passwd")
    public ModelAndView passwd(){
        ModelAndView mv = new ModelAndView("user/passwd");
        mv.addObject("curNav","passwd");

        return mv;
    }

    /**
     * 保存密码
     * @param oldPassword
     * @param password
     * @param rePassword
     * @return
     */
    @RequestMapping("/savePasswd")
    @ResponseBody
    public String savePasswd(String oldPassword,String password,String rePassword){
        AuthUser currentUser = authUserService.getById(SessionContext.getUserId());
        if (null == currentUser){
            return JsonView.render(1,"用户不存在");
        }
        oldPassword = EncryptUtil.encodedByMD5(oldPassword.trim());
        if (!oldPassword.equals(currentUser.getPassword())){
            return JsonView.render(1,"旧密码不正确");
        }
        if (!StringUtils.isNotEmpty(password.trim())){
            return JsonView.render(1,"新密码不能为空");
        }
        if (!password.trim().equals(rePassword.trim())){
            return JsonView.render(1,"两次输入密码不一致!");
        }
        currentUser.setPassword((EncryptUtil.encodedByMD5(password)));
        authUserService.updateSelectivity(currentUser);
        return new JsonView().toString();
    }

    /**
     * 问答
     * @param page
     * @return
     */
    @RequestMapping("/qa")
    public ModelAndView qa(TailPage<CourseComment> page){
        ModelAndView mv = new ModelAndView("user/qa");
        mv.addObject("curNav","qa");

        CourseComment queryEntity = new CourseComment();
        queryEntity.setUsername(SessionContext.getUsername());
        page = courseCommentService.queryPage(queryEntity,page);
        mv.addObject("page",page);

        return mv;
    }
}
