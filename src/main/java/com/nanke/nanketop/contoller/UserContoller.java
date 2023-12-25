package com.nanke.nanketop.contoller;

import cn.xbjstd.xiaobao.util.Json;
import com.nanke.nanketop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserContoller {
    @Autowired
    private UserService userService;
    Json json=new Json();
    @PostMapping("/Register")
    public Json Register(String nickname,String username, String password){
        if (userService.quireUsername(username)!=null){
            json.json(501,"该用户已存在",null);
            return json;
        }
        if (userService.Register(nickname,username,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            json.json(501,"注册失败",null);
        }else {
            json.json(200,"注册成功",null);
        }
        return json;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/Login")
    public Json Login(String username, String password){
        if (userService.quireUsername(username)==null){
            json.json(404,"该用户不存在",null);
            return json;
        }
        if(userService.quireUsernameByPassword(username,DigestUtils.md5DigestAsHex(password.getBytes()))==null){
            json.json(501,"账号或密码错误",null);
        }else {
            json.json(200,"登录成功",userService.quireUsernameByPassword(username,DigestUtils.md5DigestAsHex(password.getBytes())));
        }
        return json;
    }

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    @PostMapping("/userInfo")
    public Json userInfo(String username){
        json.json(200,"获取成功",userService.userInfo(username));
        return json;
    }

    /**
     * 根据用户名修改用户信息
     * @param username
     * @param avatar
     * @param nickname
     * @return
     */
    @PostMapping("/updateUserInfo")
    public Json updateUserInfo(String username,String avatar,String nickname){
        if (userService.updateUserInfo(username,avatar,nickname)==0){
            json.json(501,"修改失败",null);
        }else {
            json.json(200,"修改成功",null);
        }
        return json;
    }

    /**
     * 根据用户名修改密码
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/updatePasswoed")
    public Json updatePasswoed(String username,String password){
        if (userService.updatePasswoed(username,DigestUtils.md5DigestAsHex(password.getBytes()))==0){
            json.json(501,"修改失败",null);
        }else {
            json.json(200,"修改成功",null);
        }
        return json;
    }
}
