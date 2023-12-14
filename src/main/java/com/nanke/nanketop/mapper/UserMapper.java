package com.nanke.nanketop.mapper;

import com.nanke.nanketop.model.User;
import com.nanke.nanketop.model.Vote;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    @Select("SELECT * FROM `user` WHERE username=#{username}")
    User quireUsername(String username);

    /**
     * 查询账号密码是否一致
     * @param username
     * @param password
     * @return
     */
    @Select("SELECT * FROM `user` WHERE username=#{username} AND password=#{password}")
    User quireUsernameByPassword(String username,String password);
    /**
     * 注册
     * @param username
     * @param password
     * @param nickname
     * @return
     */
    @Insert("INSERT INTO `user` (avatar,nickname,username,password) VALUES ('./DefaultAvatar.png',#{nickname},#{username},#{password});")
    int Register(String nickname,String username, String password);

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    @Select("SELECT DISTINCT u.*, v.* FROM user u LEFT JOIN vote v ON u.username = v.user_username WHERE u.username = #{username}")
    List<Vote> userInfo(String username);

    /**
     * 根据用户名修改用户信息
     * @param username
     * @param avatar
     * @param nickname
     * @return
     */
    @Update("UPDATE `user` SET avatar=#{avatar},nickname=#{nickname} WHERE username=#{username}")
    int updateUserInfo(String username,String avatar,String nickname);

    /**
     * 根据用户名修改密码
     * @param username
     * @param password
     * @return
     */
    @Update("UPDATE `user` SET password=#{password} WHERE username=#{username}")
    int updatePasswoed(String username,String password);
}
