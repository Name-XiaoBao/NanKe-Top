package com.nanke.nanketop.Util;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

/**
 * @author 小暴
 */
@Service
public class EmailUtil {
    @Resource
    JavaMailSenderImpl sender;
    @Resource
    TemplateEngine templateEngine;
    @Value("${NanKeTop.email}")
    public String Email;
    @Value("${NanKeTop.name}")
    public String Name;
    /**
     * 发送普通文本邮件
     *
     * @param username
     * @param Title
     * @param Content
     */
    public void TextEmail(String username, String Title, String Content) {
        // 用来设置邮件信息
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置邮件标题
        simpleMailMessage.setSubject(Title);
        // 设置邮件内容
        simpleMailMessage.setText(Content);
        // 设置源邮箱
        simpleMailMessage.setFrom(Email);
        // 设置目的邮箱
        simpleMailMessage.setTo(username);
        // 发送
        sender.send(simpleMailMessage);
    }
    /**
     * 发送模板邮件
     * @param username
     * @param Title
     * @param Content
     * @throws MessagingException
     */
    public void TemplateEmail(String username, String Title,String Content) throws MessagingException {
        //扩展邮件信息类
        MimeMessage mimeMessage = sender.createMimeMessage();
        //添加邮件内容的辅助器
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        //发送者
        helper.setFrom(Email);
        //接收者
        helper.setTo(username);
        //邮箱标题
        helper.setSubject(Title);
        //设置发送日期
        helper.setSentDate(new Date());
        //创建一个环境，用于设置变量 org.thymeleaf.context.Context;
        Context context = new Context();
        context.setVariable("Content",Content);
        context.setVariable("Title",Title);
        //生成模板 字符串格式
        String process = templateEngine.process("email/TextEmail.html", context);
        helper.setText(process,true);
        sender.send(mimeMessage);
    }
    /**
     * 发送邮箱验证码模板邮件
     * @param email
     * @param Content
     * @throws MessagingException
     */
    public void TemplateCodeEmail(String email,String Content) throws MessagingException {
        //扩展邮件信息类
        MimeMessage mimeMessage = sender.createMimeMessage();
        //添加邮件内容的辅助器
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        //发送者
        helper.setFrom(Email);
        //接收者
        helper.setTo(email);
        //邮箱标题
        helper.setSubject(Name);
        //设置发送日期
        helper.setSentDate(new Date());
        //创建一个环境，用于设置变量 org.thymeleaf.context.Context;
        Context context = new Context();
        context.setVariable("Content",Content);
        context.setVariable("Title",Name);
        //生成模板 字符串格式
        String process = templateEngine.process("email/CodeEmail.html", context);
        helper.setText(process,true);
        sender.send(mimeMessage);
    }
    /**
     * 发送邮箱反馈模板邮件
     * @param Content
     * @throws MessagingException
     */
    public void TemplateFeedbackEmail(String Content,String ContactInformation) throws MessagingException {
        //扩展邮件信息类
        MimeMessage mimeMessage = sender.createMimeMessage();
        //添加邮件内容的辅助器
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        //发送者
        helper.setFrom(Email);
        //接收者
        helper.setTo(Email);
        //邮箱标题
        helper.setSubject(Name);
        //设置发送日期
        helper.setSentDate(new Date());
        //创建一个环境，用于设置变量 org.thymeleaf.context.Context;
        Context context = new Context();
        context.setVariable("Content",Content);
        context.setVariable("Title",Name);
        context.setVariable("ContactInformation",ContactInformation);
        //生成模板 字符串格式
        String process = templateEngine.process("email/FeedbackEmail.html", context);
        helper.setText(process,true);
        sender.send(mimeMessage);
    }
}
