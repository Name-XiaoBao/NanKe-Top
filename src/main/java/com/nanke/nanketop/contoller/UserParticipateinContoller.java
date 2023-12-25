package com.nanke.nanketop.contoller;

import cn.xbjstd.xiaobao.util.Json;
import cn.xbjstd.xiaobao.util.Uuid;
import com.nanke.nanketop.service.UserParticipateinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserParticipateinContoller {
    @Autowired
    private UserParticipateinService userParticipateinService;
    Json json=new Json();
    Uuid uuid=new Uuid();

    /**
     * 参与投票
     * @param participatingUsers
     * @param voteUid
     * @param picture
     * @param content
     * @return
     */
    @PostMapping("/ParticipateInVoting")
    public Json ParticipateInVoting(String participatingUsers,long voteUid,String picture,String title,String content){
        if (userParticipateinService.根据username和uid查询是否参与投票(participatingUsers,voteUid)==null){
            try {
                if(userParticipateinService.insertUserParticipatein(uuid.generateRandomNumber(),participatingUsers,voteUid,picture,title,content)==1){
                    json.json(200,"参与成功",null);
                }
                return json;
            }catch (Exception err){
                json.json(501,"参与失败",err);
                return json;
            }
        }else {
            json.json(501,"你已经参与过啦~\n一人只能参与一次哦~",null);
            return json;
        }
    }

    /**
     * 根据UID查询参与投票选手的信息
     * @param uid
     * @return
     */
    @PostMapping("/QueryBasedOnUid")
    public Json QueryBasedOnUid(long uid){
        json.json(200,"查询成功",userParticipateinService.QueryBasedOnUid(uid));
        return json;
    }

    /**
     * 根据username查询参与的投票
     * @param username
     * @return
     */
    @PostMapping("/QueryParticipatingUsers")
    public Json 参与的投票(String username){
        json.json(200,"查询成功",userParticipateinService.参与的投票(username));
        return json;
    }
}
