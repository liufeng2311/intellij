package com.beiming.entity.log;

import lombok.Data;

@Data
public class LogInfo {
	
	private String className;  //类名
	
	private String methodName; //方法名
	
	private String startTime; //开始时间
	
	private long useTime; //接口用时(毫秒)
	
	private String url; //接口地址
	
	private String requestType; //请求类型
	
	private String IP; //IP地址
	
	private Object param; //参数
	
	private Object result; //返回值
}
