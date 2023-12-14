package com.nanke.nanketop.Util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * UUID
 * @author 小暴
 */
public class Uuid {
    UUID uuid = UUID.randomUUID();
    public String uuid(){
        String randomString = usingUUID();
        return randomString.substring(0, 8);
    }
    static String usingUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("-", "");
    }

    /**
     * 用时间戳+两位随机数生成UID
     * @return
     */
    public static long generateRandomNumber() {
        // 创建一个Set来存储已经生成过的随机数
        Set<Long> set = new HashSet<>();

        Random random = new Random();
        long number;

        do {
            // 获取当前时间戳（毫秒级）
            long timestamp = System.currentTimeMillis();

            // 生成两个随机数
            int random1 = random.nextInt(10);
            int random2 = random.nextInt(10);

            // 将两个随机数分别插入到时间戳前后
            number = timestamp / 10 * 10 + random1 * 1000 + random2;
        } while (!set.add(number)); // 如果添加到set失败，说明这个数字已经存在，需要重新生成

        return number;
    }
}
