package com.beiming.modules.base.service;

import com.beiming.modules.sys.user.domain.entity.User;
import org.apache.shiro.SecurityUtils;

/**
 * Controller公共组件
 */
public abstract class AbstractService {

    /**
     * 获取用户信息
     * @return
     */
    protected User getUser(){
        return (User)SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取用户ID
     * @return
     */
    protected Integer getUserId(){
        return getUser().getId();
    }

    /**
     * 获取用户名
     * @return
     */
    protected String getUserName(){
        return getUser().getUsername();
    }

    /**
     * 获取用户手机号
     * @return
     */
    protected String getUserPhone(){
        return getUser().getPhone();
    }
}
