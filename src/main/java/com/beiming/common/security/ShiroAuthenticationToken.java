package com.beiming.common.security;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * shiro用户信息包装类(用于包装前端传过来的用户名密码,此处我们使用的是token)
 */

public class ShiroAuthenticationToken implements AuthenticationToken {

    private String token;

    public ShiroAuthenticationToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
