package com.nanke.nanketop.mapper;

import com.nanke.nanketop.model.Vote;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VoteMapper {
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
    @Insert("insert into `vote` (user_username,uid,cover,title,deadline,registration_time,end_registration_time,vote_num,backgroundMusic,rule,views,votes) VALUES (#{user_username},#{uid},#{cover},#{title},#{deadline},#{registration_time},#{end_registration_time},#{vote_num},#{backgroundMusic},#{rule},0,0)")
    int addVote(String user_username,long uid,String cover,String title,String deadline,String registration_time,String end_registration_time,int vote_num,String backgroundMusic,String rule);
    /**
     * 根据UID增加浏览量
     * @param uid
     * @return
     */
    @Update("UPDATE vote SET views = views + 1 WHERE uid =  #{uid}")
    int addViews(long uid);
    /**
     *获取投票列表
     * @return
     */
    @Select("SELECT vote.*, user.nickname, user.username, user.avatar FROM vote INNER JOIN user ON vote.user_username = user.username;")
    List<Vote> getVoteList();

    /**
     * 根据UID查询投票
     * @param uid
     * @return
     */
    @Select("SELECT vote.*, user.nickname, user.username, user.avatar FROM vote INNER JOIN user ON vote.user_username = user.username WHERE vote.uid = #{uid}")
    List <Vote> getVoteByUid(long uid);

    /**
     * 根据username查询投票
     * @param username
     * @return
     */
    @Select("SELECT vote.*, user.nickname, user.username, user.avatar FROM vote INNER JOIN user ON vote.user_username = user.username WHERE vote.user_username = #{username}")
    List <Vote> getVoteByUsername(String username);
}
