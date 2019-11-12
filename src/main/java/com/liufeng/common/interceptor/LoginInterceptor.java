package com.liufeng.common.interceptor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * 继承HandlerInterceptor或HandlerInterceptorAdapter拦截器，com.beiming.common.config.WebMvcConfig中进行实例化和配置拦截规则
 *	用于统计方法的执行时间
 */
@Slf4j 
public class LoginInterceptor implements  HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("方法执行前");
		request.setAttribute("startTime", LocalDateTime.now());
		return true;  //返回true表示放行
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LocalDateTime startTime = (LocalDateTime) request.getAttribute("startTime");
		log.info("方法执行完后，解析视图之前执行");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("视图渲染完成之后");
	}
	
	
}
