package com.nanke.nanketop.model;

import lombok.Data;

@Data
public class Voter {
    // 投票的IP地址
    private String ipAddress;
    // 投票的UID
    private long voteUid;
    // 参与投票的UID
    private long participatingUid;
}
