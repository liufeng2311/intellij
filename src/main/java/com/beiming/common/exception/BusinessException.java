package com.beiming.common.exception;

import com.beiming.common.enums.ResultCodeEnums;
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
