package com.nanke.nanketop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nanke.nanketop.mapper")
public class NanKeTopApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanKeTopApplication.class, args);
    }

}
