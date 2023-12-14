package com.nanke.nanketop.model;

import lombok.Data;

@Data
public class Vote {
    // UID
    private long uid;
    // 封面
    private String cover;
    // 标题
    private String title;
    // 截至日期
    private String deadline;
    // 报名日期
    private String registrationTime;
    // 截止报名日期
    private String endRegistrationTime;
    // 发起人头像
    private String avatar;
    // 发起人昵称
    private String nickname;
    // 规则详情
    private String rule;
    // 投票次数
    private int voteNum;
    // 背景音乐
    private String backgroundMusic;
    // 浏览量
    private int views;
    // 累计票数
    private int votes;
}
