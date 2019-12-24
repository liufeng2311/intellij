package com.beiming.common.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义Shiro过滤器
 * 过滤器是否放行的条件为isAccessAllowed()||onAccessDenied()
 * 验证失败执行onLoginFailure方法,该方法抛出的异常由BusinessErrorController处理
 */
public class ShiroFilter extends AuthenticatingFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        if (token == null) throw new IncorrectCredentialsException("凭证信息不能为空");
        return new ShiroAuthenticationToken(token);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) throws RuntimeException {
        if (e != null) throw e;
        return false;
    }

}

