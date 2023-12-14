package com.nanke.nanketop.model;

import lombok.Data;

@Data
public class UserParticipatein {
    // 参与人员的名字
    private String nickname;
    // 参与人员的头像
    private String avatar;
    // 参与人员UID
    private long participatingUid;
    // 参与人员
    private String participatingUsers;
    // 参与的投票封面
    private String cover;
    // 参与的投票UID
    private long voteUid;
    // 投票截至时间
    private String deadline;
    // 参与的图片
    private String picture;
    // 参与的标题
    private String title;
    // 参与的内容
    private String content;
    // 获得的票数
    private int numberOfVotes;
}
