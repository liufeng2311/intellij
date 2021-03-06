package com.liufeng;

import com.liufeng.common.async.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2  //开启Swagger文档
@EnableAsync  //开启异步任务
@EnableScheduling //开启定时任务
public class IntellijIdeaJavaApplication {


    public static void main(String[] args) {

        SpringApplication.run(IntellijIdeaJavaApplication.class, args);
    }

}
