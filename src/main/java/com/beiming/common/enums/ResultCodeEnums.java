package com.beiming.common.enums;

/**
 * 异常信息枚举类
 *
 */
public enum ResultCodeEnums {
	
	UNKNOWN_EXCEPTION("000000", "未知异常"),
	
	REQUEST_SUCCESS("100000", "请求处理成功"),
	
	//参数认证异常以2开头
	ILLEGAL_PARAMETER("200001", "参数赋值异常"),
	
	ILLEGAL_BIND_PARAMETER("200002", "参数绑定异常"),

	VAILD_PARAMETER("201003", ""),  //用于抛出参数逻辑上的异常，异常信息自定义
	
	//用户信息异常以3开头
	USER_EXCEPTION("30001", ""),
	
	//数据库异常以3开头
	BAD_SQL_DELETE("40001", "数据库操作失败"),

	//业务数据库异常
	BAD_SQL_CHECK("40002", ""),

	//业务数据库异常
	NULL_EXCEPTION("40003", "空指针异常"),

	//shiro验证异常
    VAILD_TOKEN("50001", "Authorization认证失败");
    private String code;
	
	private String message;

	private ResultCodeEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	//可自定义异常信息描述
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
