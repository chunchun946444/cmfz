package com.baizhi.cmfz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@tk.mybatis.spring.annotation.MapperScan("com.baizhi.cmfz.dao")
@MapperScan("com.baizhi.cmfz.dao")
@SpringBootApplication
public class CmfzZcApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmfzZcApplication.class, args);
    }

}
