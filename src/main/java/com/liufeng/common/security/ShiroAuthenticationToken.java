package com.liufeng.common.security;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * AuthenticationToken类是用来接受我们前台传来的username,password的封装类,每一个Realm都会对应一个AuthenticationToken,
 * 所以每一个Realm中的supports()方法会对AuthenticationToken进行验证，判断AuthenticationToken是否为本Realm支持的
 * 这里我们使用JWTToken,接收的是token,从token中解析出username,password
 */

public class ShiroAuthenticationToken implements AuthenticationToken {
	
	private String token;

    public ShiroAuthenticationToken(String token){
        this.token = token;
    }

    //获取用户名,默认框架中会把username属性的值赋值给它
    //此处我们返回token,并从中解析出JWT设置的相关信息
    @Override
    public String getPrincipal() {
        return token;
    }

    //获取密码,默认框架中会把password属性的值赋值给它
    //此处我们返回token,并从中解析出JWT设置的相关信息
    @Override
    public Object getCredentials() {
        return token;
    }
}
