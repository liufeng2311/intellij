package com.beiming.common.aspect;

import com.alibaba.fastjson.JSON;
import com.beiming.common.security.JWTToken;
import com.beiming.common.utils.DateUtils;
import com.beiming.common.utils.IPUtils;
import com.beiming.modules.base.log.LogInfo;
import com.beiming.modules.sys.user.domain.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * 日志拦截器
 */
@Aspect
@Component
@Slf4j         //Slf4j是接口的框架定义(Simple Logging Facade For Java的简称)
public class RequestLogAspect {
	
	@Pointcut("execution(* com.beiming.modules..*.controller..*.*(..))")  //拦截controller里的所有接口
	public void requestLog() {
		
	}
	
	@Around("requestLog()")
	public Object logger(ProceedingJoinPoint point) throws Throwable {
		LogInfo info = new LogInfo();
		HttpServletRequest request  = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); //获取request对象
		if(point.getArgs() != null && point.getArgs().length > 0){
			info.setParam(point.getArgs()[0]); //获取被调用方法的类型
		}
		String token = request.getHeader("Authorization");
		if(token == null){
			token = request.getParameter("Authorization");
		}
		if(!StringUtils.isEmpty(token)){
		info.setUsername(JSON.parseObject(JWTToken.parseJWT(token).getSubject(), SysUser.class).getPhone());
		}
		Instant start = Instant.now(); //方法执行开始时间
		info.setStartTime(DateUtils.localDateTime2String(LocalDateTime.now(),DateUtils.DATETIME_FORMATTER)); //获取接口调用时间
		Object result = point.proceed(); //执行方法
		Instant end = Instant.now(); //方法执行结束时间
		info.setClassName(point.getTarget().getClass().getName()); //获取被调用方法的类名
		info.setMethodName(point.getSignature().getName()); //获取被调用方法的方法名
		info.setIP(IPUtils.getRealAddress(request)); //获取IP
		info.setResult(result); //获取返回值
		info.setRequestType(request.getMethod()); //获取请求方式
		info.setUrl(request.getRequestURL().toString()); //获取URL
		info.setUseTime(Duration.between(start, end).toMillis()); //获取接口执行时间
		log.info("接口调用:{}",	JSON.toJSONString(info));
		return result;
	}


}
