package com.beiming.modules.base.service;

import com.beiming.modules.base.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Service层公共父类
 * 定义service中的用法用法
 */
public abstract class AbstractService{

    /**
     * 获取用户信息
     */
    public SysUser getUser(){
        return (SysUser)SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }

    /**
     * 获取HttpServletResponse
     */
    public HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
    }
}
