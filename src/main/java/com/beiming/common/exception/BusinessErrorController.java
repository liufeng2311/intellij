package com.beiming.common.exception;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.utils.ResultModel;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常只能捕获Controller层抛出的异常
 * 对于Filter中的异常无法捕获,Filter中的异常会发送错误请求重定向,框架中由ErrorController处理
 * 此处的异常需要让前端跳转至登录页
 */

@RestController
public class BusinessErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public ResultModel filterError(HttpServletRequest request, HttpServletResponse response){
        Exception e = (Exception) request.getAttribute("javax.servlet.error.exception"); //获取Filter抛出的异常信息
        if(e != null){
            return ResultModel.fail(ResultCodeEnums.VAILD_TOKEN,e.getCause().getMessage());
        }else {
            return ResultModel.fail(ResultCodeEnums.UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
