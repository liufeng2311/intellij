package com.beiming;

import com.beiming.test.domain.ScoreTest;
import com.beiming.test.mapper.ScoreTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAsync  //开启异步任务
@EnableScheduling //开启定时任务
@RestController
@RequestMapping("test")
public class IntellijIdeaJavaApplication {

    @Autowired
    ScoreTestMapper scoreTestMapper;

    public static void main(String[] args) {

        SpringApplication.run(IntellijIdeaJavaApplication.class, args);
    }

    @RequestMapping("/anon/test")
    public Object test(){
        ScoreTest scoreTest = scoreTestMapper.selectByPrimaryKey1(1);
        return scoreTest;
    }

}
