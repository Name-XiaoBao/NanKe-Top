package com.nanke.nanketop.service;

import com.nanke.nanketop.mapper.UserParticipateinMapper;
import com.nanke.nanketop.model.UserParticipatein;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserParticipateinService {
    @Autowired
    private UserParticipateinMapper userParticipateinMapper;

    /**
     * 参加投票
     * @param participatingUid
     * @param participatingUsers
     * @param voteUid
     * @param picture
     * @param content
     * @return
     */
    public int insertUserParticipatein(long participatingUid,String participatingUsers,long voteUid,String picture,String title,String content){
        return userParticipateinMapper.AddParticipation(participatingUid,participatingUsers,voteUid,picture,title,content);
    }

    /**
     * 根据UID查询参与投票选手的信息
     * @param uid
     * @return
     */
    public List<UserParticipatein> QueryBasedOnUid(long uid){
        return userParticipateinMapper.QueryBasedOnUid(uid);
    }

    /**
     * 根据username和uid查询是否参与投票
     *
     * @param username
     * @param uid
     * @return
     */
    public UserParticipatein 根据username和uid查询是否参与投票(String username, long uid){
        return userParticipateinMapper.根据username和uid查询是否参与投票(username,uid);
    }

    /**
     * 根据username查询参与的投票
     * @param username
     * @return
     */
    public List<UserParticipatein> 参与的投票(String username){
        return userParticipateinMapper.参与的投票(username);
    }
}
