package com.beiming.common.exception;

import com.beiming.common.enums.ResultCodeEnums;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义异常
 */

@Data
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(ResultCodeEnums enums, String message) {
        super(message);
        this.code = enums.getCode();
    }

}
