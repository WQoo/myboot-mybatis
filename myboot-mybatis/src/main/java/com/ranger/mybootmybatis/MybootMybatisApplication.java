package com.ranger.mybootmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ranger.**.mapper")
public class MybootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybootMybatisApplication.class, args);
    }

}
