package com.online.web;

import com.google.code.kaptcha.Constants;
import com.online.common.util.RandomUtil;
import com.online.common.web.SessionContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Properties;

public class EmailUtil {


    public String toEmail(String toUserName, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String subject = "验证码";
        String html = "本次注册的验证码为：";
        RandomUtil rm = new RandomUtil();
        String code = rm.generateWord();
        session.setAttribute("code",code);

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.qq.com");
        sender.setPort(465);
        sender.setUsername("1131727441@qq.com");
        sender.setPassword("onurexaslgzmhjac"); // 这里要用邀请码，不是你登录邮箱的密码

        Properties pro = System.getProperties(); // 下面各项缺一不可
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.ssl.enable", "true");
        pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        sender.setJavaMailProperties(pro);

        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("蚌埠学院"); // 发送人
            helper.setTo(toUserName); // 收件人
            helper.setSubject(subject); // 标题
            helper.setText(html + code); // 内容
            sender.send(message);
            System.out.println("发送完毕！");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
}
