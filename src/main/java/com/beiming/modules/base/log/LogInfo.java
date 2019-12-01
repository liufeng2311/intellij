package com.beiming.modules.base.log;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
/**
 * 接口调用日志
 */
@Data
public class LogInfo {

	@JSONField(name = "用户名")
	private String username;

	@JSONField(name = "类名")
	private String className;

	@JSONField(name = "方法名")
	private String methodName;

	@JSONField(name = "开始时间")
	private String startTime;

	@JSONField(name = "接口用时(毫秒)")
	private long useTime;

	@JSONField(name = "url")
	private String url;

	@JSONField(name = "请求类型")
	private String requestType;

	@JSONField(name = "IP")
	private String IP;

	@JSONField(name = "参数")
	private Object param;

	@JSONField(name = "返回值")
	private Object result;
}
