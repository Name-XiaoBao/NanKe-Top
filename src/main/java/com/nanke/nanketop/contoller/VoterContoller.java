package com.nanke.nanketop.contoller;

import cn.xbjstd.xiaobao.util.Json;
import com.nanke.nanketop.service.VoterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoterContoller {
    @Autowired
    private VoterService voterService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 根据uid和参与选手uid进行投票
     * @param voteUid
     * @param participatingUid
     * @return
     */
    @PostMapping("/Voter")
    public Json insertVoter(long voteUid, long participatingUid){
        try {
            String clientIp = getClientIp();
            if (voterService.insertVoter(clientIp, voteUid, participatingUid)){
                return Json.json(200, "投票成功", null);
            }else {
                return Json.json(404, "今天的投票次数已经没有啦！\n请明天再来吧~", null);
            }
        }catch (Exception e){
            return Json.json(500, "投票失败", e);
        }
    }

    private String getClientIp() {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isEmpty()) {
            return xff.split(",")[0];
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isEmpty()) {
            return realIp;
        }
        return request.getRemoteAddr();
    }
}
