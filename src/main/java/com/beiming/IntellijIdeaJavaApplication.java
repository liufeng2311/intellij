package com.beiming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync  //开启异步任务
@EnableScheduling //开启定时任务
public class IntellijIdeaJavaApplication {


    public static void main(String[] args) {

        SpringApplication.run(IntellijIdeaJavaApplication.class, args);
    }

}
