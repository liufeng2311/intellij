package com.liufeng.common.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务
 */
@Async //该注解用于类上，表示所有的方法都是异步执行
@Component
public class AsyncTask {

    public void testAsync(){

    }


}
