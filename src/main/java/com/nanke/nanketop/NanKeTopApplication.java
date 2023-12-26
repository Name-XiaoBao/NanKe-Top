package com.nanke.nanketop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"com.nanke.nanketop", "cn.xbjstd.xiaobao"})
@ComponentScan({"com.nanke.nanketop", "cn.xbjstd.xiaobao"})
@EnableScheduling
public class NanKeTopApplication {
    public static void main(String[] args) {
        SpringApplication.run(NanKeTopApplication.class, args);
        System.out.println("启动成功╰(*°▽°*)╯");
    }
}
