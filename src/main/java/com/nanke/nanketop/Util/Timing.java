package com.nanke.nanketop.Util;

import com.nanke.nanketop.mapper.VoterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每日定时任务
 */
@Component
public class Timing {
    @Autowired
    private VoterMapper voterMapper;
    /**
     * 清空voter数据表
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearVoterData() {
        voterMapper.truncateVoterTable();
    }
}
