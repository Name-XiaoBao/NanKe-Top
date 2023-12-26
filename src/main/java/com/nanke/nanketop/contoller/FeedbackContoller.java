package com.nanke.nanketop.contoller;

import cn.xbjstd.xiaobao.util.Json;
import cn.xbjstd.xiaobao.util.LimitRequest;
import com.nanke.nanketop.Util.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackContoller {
    @Autowired
    private EmailUtil emailUtil;
    @RequestMapping("/Feedback")
    @LimitRequest(time = 180000, count = 1, msg = "请求过于频繁")
    public Json Feedback(String Content, String ContactInformation) {
        try {
            emailUtil.TemplateFeedbackEmail(Content,ContactInformation);
            return Json.json(200, "发送成功", null);
        } catch (MessagingException e) {
            return Json.json(501, "发送失败", e);
        }
    }
}
