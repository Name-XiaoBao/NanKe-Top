package com.nanke.nanketop.contoller;

import com.nanke.nanketop.Util.Json;
import com.nanke.nanketop.Util.LimitRequest;
import com.nanke.nanketop.Util.Uuid;
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
    Json json = new Json();
    Uuid uuid = new Uuid();
    @LimitRequest(time = 86400000, count = 1,msg = "每24小时只能发布一次哦~\n距离上一次发布还不到24小时呢！")
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
    public Json addVote(String user_username,String cover,String title,String deadline,String registration_time,String end_registration_time,int vote_num,String backgroundMusic,String rule){
        try {
            if(voteService.addVote(user_username,uuid.generateRandomNumber(),cover,title,deadline,registration_time,end_registration_time,vote_num,backgroundMusic,rule)==1){
                json.json(200,"创建成功",null);
            }
            return json;
        }catch (Exception err){
            json.json(501,"创建失败",err);
            return json;
        }
    }

    /**
     * 根据uid增加浏览量
     * @param uid
     * @return
     */
    @PostMapping("/addViews")
    public Json addViews(long uid){
        json.json(200,"浏览量+1",voteService.addViews(uid));
        return json;
    }
    /**
     * 获取投票列表
     * @return
     */
    @PostMapping("/getVoteList")
    public Json getVoteList(){
        json.json(200,"获取投票列表成功",voteService.getVoteList());
        return json;
    }

    /**
     * 根据UID查询投票
     * @param uid
     * @return
     */
    @PostMapping("/getVoteByUid")
    public Json getVoteByUid(long uid) {
        if (voteService.getVoteByUid(uid) != null){
            json.json(200, "获取成功", voteService.getVoteByUid(uid));
        }else {
            json.json(404, "该投票已被删除或不存在！", voteService.getVoteByUid(uid));
        }
        return json;
    }

    /**
     * 根据username查询投票
     * @param username
     * @return
     */
    @PostMapping("/getVoteByUsername")
    public Json getVoteByUsername(String username){
        json.json(200,"获取投票列表成功",voteService.getVoteByUsername(username));
        return json;
    }
}
