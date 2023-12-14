package com.nanke.nanketop.mapper;

import com.nanke.nanketop.model.UserParticipatein;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserParticipateinMapper {
    /**
     * 参加投票
     * @param participatingUid
     * @param participatingUsers
     * @param voteUid
     * @param picture
     * @param content
     * @return
     */
    @Insert("INSERT INTO user_participatein(participating_uid,participating_users,vote_uid,picture,title,content) VALUES (#{participatingUid},#{participatingUsers},#{voteUid},#{picture},#{title},#{content});")
    int AddParticipation(long participatingUid,String participatingUsers,long voteUid,String picture,String title,String content);
    /**
     * 根据投票UID查询所有参与人员的用户信息
     * @param uid
     * @return
     */
    @Select("SELECT user.*, up.* FROM user INNER JOIN user_participatein AS up ON user.username = up.participating_users WHERE up.vote_uid = #{uid};")
    List <UserParticipatein> userParticipatein(long uid);

    /**
     * 根据UID查询参与投票选手的信息
     * @param uid
     * @return
     */
    @Select("SELECT up.*, u.nickname, u.avatar, v.deadline FROM user_participatein up JOIN user u ON up.participating_users = u.username JOIN vote v ON up.vote_uid =v.uid WHERE up.vote_uid = #{uid} ORDER BY up.number_of_votes DESC;")
    List<UserParticipatein> QueryBasedOnUid(long uid);

    /**
     * 根据username查询参与的投票
     * @param username
     * @return
     */
    @Select("SELECT v.*, u.avatar, up.number_of_votes, up.vote_uid FROM vote v INNER JOIN user_participatein up ON v.uid = up.vote_uid INNER JOIN user u ON u.username = up.participating_users WHERE up.participating_users = #{username};")
    List<UserParticipatein> 参与的投票(String username);

    /**
     * 根据username和uid查询是否参与投票
     * @param username
     * @param uid
     * @return
     */
    @Select("SELECT * FROM user_participatein WHERE participating_users = #{username}  AND vote_uid = #{uid};")
    UserParticipatein 根据username和uid查询是否参与投票(String username,long uid);
}
