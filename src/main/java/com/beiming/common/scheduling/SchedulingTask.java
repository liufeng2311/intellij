package com.beiming.common.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
@Async //定时任务默认是单线程顺序执行,此处使用异步执行，使用的我们自定义的线程池
@Slf4j
public class SchedulingTask {

    @Scheduled(cron = "0 0 0/1 * * ? ")  //每小时执行一次
    public void schedulingTest(){
        log.info("定时任务执行");
    }

}
