package com.f.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by matf on 2020-04-18.
 */

@SpringBootApplication
@ComponentScan({"com.f.demo.common",
        "com.f.demo.module",
        "com.f.demo.utils"})
@MapperScan({"com.f.demo.module"})
@ServletComponentScan
@EnableScheduling
public class FApplication {


    public static void main(String[] args) {

        SpringApplication.run(FApplication.class, args);
    }

}
