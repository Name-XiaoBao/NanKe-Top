package com.nanke.nanketop.service;

import com.nanke.nanketop.mapper.UserParticipateinMapper;
import com.nanke.nanketop.mapper.VoteMapper;
import com.nanke.nanketop.model.UserParticipatein;
import com.nanke.nanketop.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService    {
    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private UserParticipateinMapper userParticipateinMapper;
    /**
     * 创建投票
     * @param user_username
     * @param uid
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
    public int addVote(String user_username,long uid,String cover,String title,String deadline,String registration_time,String end_registration_time,int vote_num,String backgroundMusic,String rule){
        return voteMapper.addVote(user_username,uid,cover,title,deadline,registration_time,end_registration_time,vote_num,backgroundMusic,rule);
    }
    /**
     * 根据UID增加浏览量
     * @param uid
     * @return
     */
    public int addViews(long uid){
        return voteMapper.addViews(uid);
    }
    /**
     * 获取投票列表
     * @return
     */
    public List<Vote> getVoteList(){
        return voteMapper.getVoteList();
    }

    /**
     * 根据UID查询投票
     * @param uid
     * @return
     */
    public List <Object> getVoteByUid(long uid){
        List <Vote> vote = voteMapper.getVoteByUid(uid);
        List <UserParticipatein> UserParticipatein = userParticipateinMapper.userParticipatein(uid);
        List<Object> result = new ArrayList<>();
        result.add(vote);
        result.add(UserParticipatein);
        return result;
    }

    /**
     * 根据username查询投票
     * @param username
     * @return
     */
    public List <Vote> getVoteByUsername(String username){
        return voteMapper.getVoteByUsername(username);
    }
}
