package com.online.portal.controller;


import com.online.common.web.SessionContext;
import com.online.web.EmailUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



@RequestMapping("/test")
@Controller
public class TestController {

    @RequestMapping("/toEm")
    public ModelAndView toEmail(){
        ModelAndView mv = new ModelAndView("test");
        return mv;
    }

    //邮箱验证测试
//    @RequestMapping("/doEm")
//    public void doEm(String mail, HttpServletResponse response, HttpServletRequest request) throws IOException, AddressException, MessagingException{
//        PrintWriter out = response.getWriter();
//        response.setCharacterEncoding("utf-8");
//        int idcode = (int) (Math.random()*100000);
//        String text = Integer.toString(idcode);
//        request.getSession().setAttribute("idcode", text);
//        EmailUtil emailUtil = new EmailUtil();
//        String code = emailUtil.toEmail(mail,request);
//        System.out.println(code);
//        out.write("success");
//    }


    @RequestMapping("/doSubmit")
    public void doSubmit(String idcode,HttpServletRequest request){
        String code = request.getSession().getAttribute("code").toString();
        if (idcode.equalsIgnoreCase(code)){
            System.out.println(true);
        }else {
            System.out.println(false);
        }
    }
}
