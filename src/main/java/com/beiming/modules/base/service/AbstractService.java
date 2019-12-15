package com.beiming.modules.base.service;

import com.beiming.modules.sys.user.domain.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Service公共父类,用于获取一些共用信息
 */
public abstract class AbstractService{

    /**
     * 获取用户信息
     * @return
     */
    public SysUser getUser(){
        return (SysUser)SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取用户ID
     * @return
     */
    public Integer getUserId(){
        return getUser().getId();
    }

    /**
     * 获取用户名
     * @return
     */
    public String getUserName(){
        return getUser().getUsername();
    }

    /**
     * 获取用户手机号
     * @return
     */
    public String getUserPhone(){
        return getUser().getPhone();
    }

    /**
     * 获取HttpServletRequest
     * @return
     */
    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }

    /**
     * 获取HttpServletResponse
     * @return
     */
    public HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
    }
}
