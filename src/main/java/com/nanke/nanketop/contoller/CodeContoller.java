package com.nanke.nanketop.contoller;

import cn.xbjstd.xiaobao.util.Json;
import cn.xbjstd.xiaobao.util.LimitRequest;
import cn.xbjstd.xiaobao.util.VerificationCode;
import com.nanke.nanketop.Util.EmailUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeContoller {
    @Autowired
    private EmailUtil emailUtil;
    Json json=new Json();
    VerificationCode Vc=new VerificationCode();
    /**
     * 发送验证码（间隔1分钟一次）
     * @param email 邮箱账号
     * @return
     */
    @LimitRequest(time = 60000,count = 1,msg = "一分钟只能发送一次哦~")
    @PostMapping("/getCode")
    public Json sendAuthCodeEmail(String email) {
        //随机验证码
        String code=Vc.generateVerifyCode(6);
        try {
            emailUtil.TemplateCodeEmail(email,"您的验证码是："+code);
            // 没有出现异常，正常发送，返回true
            json.json(200,"发送成功", DigestUtils.md5DigestAsHex(code.getBytes()));
            return json;
        } catch (MessagingException e) {
            // 发送过程中，发生错误，打印错误信息，返回false
            e.printStackTrace();
            json.json(501,"发送失败",e);
            return json;
        }
    }
}
