package com.beiming.domian.log;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class LogInfo {

	@JSONField(name = "手机号")
	private String username; //调用者手机号

	@JSONField(name = "类名")
	private String className;  //类名

	@JSONField(name = "方法名")
	private String methodName; //方法名

	@JSONField(name = "开始时间")
	private String startTime; //开始时间

	@JSONField(name = "接口用时(毫秒)")
	private long useTime; //接口用时(毫秒)

	@JSONField(name = "url")
	private String url; //接口地址

	@JSONField(name = "请求类型")
	private String requestType; //请求类型

	@JSONField(name = "IP")
	private String IP; //IP地址

	@JSONField(name = "参数")
	private Object param; //参数

	@JSONField(name = "返回值")
	private Object result; //返回值
}
