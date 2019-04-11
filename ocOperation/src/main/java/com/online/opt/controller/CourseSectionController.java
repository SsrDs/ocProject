package com.online.opt.controller;

import com.online.common.web.JsonView;
import com.online.core.course.domain.CourseSection;
import com.online.core.course.service.ICourseSectionService;
import com.online.opt.business.ICourseSectionBusiness;
import com.online.opt.vo.CourseSectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequestMapping("/courseSection")
@Controller
public class CourseSectionController {
    @Autowired
    private ICourseSectionBusiness courseSectionBusiness;

    @Autowired
    private ICourseSectionService courseSectionService;

    @RequestMapping(value = "/getById")
    @ResponseBody
    public String getById(Long id){
        return JsonView.render(courseSectionService.getById(id));
    }

    @RequestMapping(value = "/doMerge")
    @ResponseBody
    public String doMerge(CourseSection entity){
        courseSectionService.updateSelectivity(entity);
        return new JsonView().toString();
    }

    @RequestMapping(value = "/deleteLogic")
    @ResponseBody
    public String deleteLogic(CourseSection entity){
        courseSectionService.delete(entity);
        return new JsonView().toString();
    }

    //批量添加章节
    @RequestMapping(value = "/batchAdd")
    @ResponseBody
    public String batchAdd(@RequestBody List<CourseSectionVO> batchSections){
        courseSectionBusiness.batchAdd(batchSections);
        return new JsonView().toString();
    }

    /**
     * 导入excel
     * @param courseId
     * @param excelFile
     * @return
     */
    @RequestMapping("/doImport")
    @ResponseBody
    public String doImport(Long courseId, @RequestParam(value = "courseSectionExcel",required = true)MultipartFile excelFile){
        try {
            if (null != excelFile && excelFile.getBytes().length > 0 ){
                InputStream is = excelFile.getInputStream();
                courseSectionBusiness.batchImport(courseId, is);//批量导入
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonView().toString();
    }
}
