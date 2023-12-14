package com.nanke.nanketop.service;

import com.nanke.nanketop.mapper.UserMapper;
import com.nanke.nanketop.mapper.UserParticipateinMapper;
import com.nanke.nanketop.model.User;
import com.nanke.nanketop.model.UserParticipatein;
import com.nanke.nanketop.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserParticipateinMapper userParticipateinMapper;

    /**
     *  查询用户名是否存在
     * @param username
     * @return
     */
    public User quireUsername(String username){
        return userMapper.quireUsername(username);
    }

    /**
     * 查询账号密码是否一致
     * @param username
     * @param password
     * @return
     */
    public User quireUsernameByPassword(String username,String password){
        return userMapper.quireUsernameByPassword(username,password);
    }
    /**
     * 注册
     * @param username
     * @param password
     * @param nickname
     * @return
     */
    public int Register(String nickname,String username, String password){
        return userMapper.Register(nickname,username,password);
    }

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    public List <Object> userInfo(String username){
        User user =userMapper.quireUsername(username);
        List <Vote> vote = userMapper.userInfo(username);
        List <UserParticipatein> 参与的投票 = userParticipateinMapper.参与的投票(username);
        List<Object> result = new ArrayList<>();
        result.add(user);
        result.add(vote);
        result.add(参与的投票);
        return result;
    }

    /**
     * 根据用户名修改用户信息
     * @param username
     * @param avatar
     * @param nickname
     * @return
     */
    public int updateUserInfo(String username,String avatar,String nickname){
        return userMapper.updateUserInfo(username,avatar,nickname);
    }

    /**
     * 根据用户名修改密码
     * @param username
     * @param password
     * @return
     */
    public int updatePasswoed(String username,String password){
        return userMapper.updatePasswoed(username,password);
    }
}
