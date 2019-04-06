package com.online.opt.controller;

import com.online.common.page.TailPage;
import com.online.common.web.JsonView;
import com.online.core.consts.domain.ConstsCollege;
import com.online.core.consts.service.IConstsCollegeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 网校管理
 */
@RequestMapping("/college")
@Controller
public class CollegeController {
    @Autowired
    private IConstsCollegeService constsCollegeService;

    /**
     * 分页展示
     * @param queryEntity
     * @param page
     * @return
     */
    @RequestMapping("/queryPageList")
    public ModelAndView queryPage(ConstsCollege queryEntity, TailPage<ConstsCollege> page){
        ModelAndView mv = new ModelAndView("cms/college/collegePageList");
        mv.addObject("curNav","college");

        if (StringUtils.isNotEmpty(queryEntity.getName())){
            queryEntity.setName(queryEntity.getName().trim());
        }else {
            queryEntity.setName(null);
        }
        page.setPageSize(2);
        page = constsCollegeService.queryPage(queryEntity,page);
        mv.addObject("page",page);
        mv.addObject("queryEntity",queryEntity);

        return mv;
    }

    /**
     * 根据id获取数据信息
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    @ResponseBody
    public String getById(Long id){
        return JsonView.render(constsCollegeService.getById(id));
    }

    @RequestMapping("/doMerge")
    @ResponseBody
    public String doMerge(ConstsCollege constsCollege){
        if (constsCollege.getId() == null){
            ConstsCollege tmpCollege = constsCollegeService.getByCode(constsCollege.getCode());
            if (tmpCollege != null){
                return JsonView.render(1,"此编码已存在");
            }
            constsCollegeService.createSelectivity(constsCollege);
        }else {
            constsCollegeService.updateSelectivity(constsCollege);
        }

        return new JsonView().toString();
    }

    /**
     * 删除
     * @param constsCollege
     * @return
     */
    @RequestMapping("/deleteLogic")
    @ResponseBody
    public String deleteLogic(ConstsCollege constsCollege){
        constsCollegeService.deleteLogic(constsCollege);
        return  new JsonView().toString();
    }

}
