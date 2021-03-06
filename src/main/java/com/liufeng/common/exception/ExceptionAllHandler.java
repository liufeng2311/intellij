package com.liufeng.common.exception;

import java.sql.SQLException;
import java.util.stream.Collectors;

import com.liufeng.common.enums.ResultCodeEnums;
import com.liufeng.common.utils.ResultModel;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import lombok.extern.slf4j.Slf4j;

/**
 * 	统一异常捕获类
 *
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionAllHandler {
	
	//其他异常
	@ExceptionHandler(Exception.class)
	public ResultModel processException(Exception e) {
		log.error("exception = { }", e);
		return ResultModel.fail(ResultCodeEnums.UNKNOWN_EXCEPTION,ResultCodeEnums.UNKNOWN_EXCEPTION.getMessage());
	}
	
	//用于捕获接口参数绑定时的异常，@Valid抛出的异常
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResultModel  processMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
		log.error("methodArgumentNotValidException = {}", message);
		return ResultModel.fail(ResultCodeEnums.ILLEGAL_PARAMETER, message);
	}
	
	//@RequestBody绑定失败时的异常
	  @ExceptionHandler(HttpMessageNotReadableException.class)
	  public ResultModel processHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
	    log.error(" error {}", ex);
	    return ResultModel.fail(ResultCodeEnums.ILLEGAL_BIND_PARAMETER,
	    		ResultCodeEnums.ILLEGAL_BIND_PARAMETER.getMessage());
	  }
	  
	  //SQL异常
	  @ExceptionHandler(SQLException.class)
	  public ResultModel processSQLException(SQLException ex) {
	    log.error(" error {}", ex);
	    return ResultModel.fail(ResultCodeEnums.BAD_SQL_DELETE,
	    		ResultCodeEnums.BAD_SQL_DELETE.getMessage());
	  }

	//自定义异常
	@ExceptionHandler(BusinessException.class)
	public ResultModel processBusinessException(BusinessException ex) {
		log.error(" error {}", ex);
		return ResultModel.fail(ResultCodeEnums.BAD_SQL_CHECK,
				ex.getMessage());
	}
}
