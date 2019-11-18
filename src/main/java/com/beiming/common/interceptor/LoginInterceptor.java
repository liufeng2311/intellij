package com.beiming.common.interceptor;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * 拦截器、过滤器、@{@link org.aspectj.lang.annotation.Aspect} 都是AOP的实现
 * 继承HandlerInterceptor或HandlerInterceptorAdapter实现拦截器
 */
@Slf4j 
public class LoginInterceptor implements  HandlerInterceptor{

	//方法执行前执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;  //返回true表示放行
	}

	//方法执行完后，解析视图之前执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	//视图渲染完成之后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	
}
