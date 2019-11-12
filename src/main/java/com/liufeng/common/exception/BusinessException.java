package com.liufeng.common.exception;

import com.liufeng.common.enums.ResultCodeEnums;
import lombok.Data;

/**
 * 自定义异常
 *
 */
@SuppressWarnings("serial")
@Data
public class BusinessException extends RuntimeException{
	
	private String code;

	public BusinessException(ResultCodeEnums enums) {
		super(enums.getMessage());
		this.code = enums.getCode();
	}
	
	public BusinessException(ResultCodeEnums enums, String message) {
		super(message);
		this.code = enums.getCode();
	}
	
}
