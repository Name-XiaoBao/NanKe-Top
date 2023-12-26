package com.nanke.nanketop.contoller;

import cn.xbjstd.xiaobao.util.Json;
import cn.xbjstd.xiaobao.util.LimitRequest;
import cn.xbjstd.xiaobao.util.Uuid;
import com.nanke.nanketop.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取投票列表
 */
@RestController
public class VoteContoller {
    @Autowired
    private VoteService voteService;
    /**
     * 创建投票
     * @param user_username
     * @param cover
     * @param title
     * @param deadline
     * @param registration_time
     * @param end_registration_time
     * @param vote_num
     * @param backgroundMusic
     * @param rule
     * @return
     */
    @PostMapping("/addVote")
    @LimitRequest(time = 86400000, count = 1, msg = "每24小时只能发布一次哦~\n距离上一次发布还不到24小时呢！")
    public Json addVote(String user_username,String cover,String title,String deadline,String registration_time,String end_registration_time,int vote_num,String backgroundMusic,String rule){
        try {
            if (voteService.addVote(user_username, Uuid.generateRandomNumber(), cover, title, deadline, registration_time, end_registration_time, vote_num, backgroundMusic, rule) == 1) {
                return Json.json(200, "创建成功", null);
            } else {
                return Json.json(501, "创建失败", null);
            }
        } catch (Exception err) {
            return Json.json(501, "创建失败", err);
        }
    }

    /**
     * 根据uid增加浏览量
     * @param uid
     * @return
     */
    @PostMapping("/addViews")
    public Json addViews(long uid){
        return Json.json(200, "浏览量+1", voteService.addViews(uid));
    }
    /**
     * 获取投票列表
     * @return
     */
    @PostMapping("/getVoteList")
    public Json getVoteList(){
        return Json.json(200, "获取投票列表成功", voteService.getVoteList());
    }

    /**
     * 根据UID查询投票
     * @param uid
     * @return
     */
    @PostMapping("/getVoteByUid")
    public Json getVoteByUid(long uid) {
        if (voteService.getVoteByUid(uid) != null){
            return Json.json(200, "获取成功", voteService.getVoteByUid(uid));
        }else {
            return Json.json(404, "该投票已被删除或不存在！", voteService.getVoteByUid(uid));
        }
    }

    /**
     * 根据username查询投票
     * @param username
     * @return
     */
    @PostMapping("/getVoteByUsername")
    public Json getVoteByUsername(String username){
        return Json.json(200, "获取投票列表成功", voteService.getVoteByUsername(username));
    }
}
