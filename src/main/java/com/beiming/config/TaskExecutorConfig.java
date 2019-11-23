package com.beiming.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *	初始化定时任务连接池 
 *
 */
@Configuration
public class TaskExecutorConfig {
	
	@Bean //开启定时任务,默认会使用id为taskExecutor的线程池，如果我们定义的线程池不是taskExecutor,系统依旧会创建并使用id为taskExecutor的线程池
	@Primary  //刚命名不为taskExecutor时,使用该注解设置为首选连接池
	public Executor  taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		int available = Runtime.getRuntime().availableProcessors(); //获取虚拟机最大可用CPU核数
		executor.setCorePoolSize(available*2); //核心线程数设置为CPU的两倍为最佳
		executor.setMaxPoolSize(available*4); //设置最大线程数
		executor.setQueueCapacity(50); //队列容量
		executor.setKeepAliveSeconds(60); //线程多余核心线程数时,线程空余回收的秒数，默认即为60秒
		executor.setThreadNamePrefix("liufeng-"); //设置线程前缀
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //设置线程拒绝策略,此处设置为由调用者执行
		return executor;
		
	}
}
