package com.beiming.common.aspect;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.beiming.common.utils.DateUtils;
import com.beiming.common.utils.IPUtils;
import com.beiming.entity.log.LogInfo;
import com.beiming.service.DictService;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志拦截器
 */
@Aspect
@Component
@Slf4j         //Slf4j   ----> Simple Logging Facade For Java，是接口的框架定义
public class RequestLogAspect {
	
	@Autowired
	DictService loggerService;
	
	@Pointcut("execution(* com.beiming.controller..*.*(..))")  //拦截controller里的所有接口
	public void requestLog() {
		
	}
	
	@Around("requestLog()")
	public void logger(ProceedingJoinPoint point) throws Throwable {
		LogInfo info = new LogInfo();
		Instant start = Instant.now(); //方法执行开始时间
		info.setStartTime(DateUtils.localDateTime2String(LocalDateTime.now(),DateUtils.DATETIME_FORMATTER)); //获取接口调用时间
		Object result = point.proceed(); //执行方法
		Instant end = Instant.now(); //方法执行结束时间
		HttpServletRequest request  = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); //获取request对象
		info.setClassName(point.getTarget().getClass().getName()); //获取被调用方法的类名
		info.setMethodName(point.getSignature().getName()); //获取被调用方法的方法名
		info.setIP(IPUtils.getRealAddress(request)); //获取IP
		info.setParam(point.getArgs()[0]); //获取被调用方法的类型
		info.setResult(result); //获取返回值
		info.setRequestType(request.getMethod()); //获取请求方式
		info.setUrl(request.getRequestURL().toString()); //获取URL
		info.setUseTime(Duration.between(start, end).toMillis()); //获取接口执行时间
		log.debug("接口调用:{}",	JSON.toJSONString(info));
		System.out.println(log.getClass());
	}
}
