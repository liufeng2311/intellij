package com.beiming.common.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

/**
 * 自定义Shiro过滤器
 * 先判断是否放行,若放行executeLogin()登录方法
 * 登陆中会执行createToken()方法生成token
 * 成功则放行
 * 失败执行onLoginFailure方法
 *
 */
public class ShiroFilter extends AuthenticatingFilter {


    //过滤器是否放行的条件为isAccessAllowed()||onAccessDenied(),我们可以直接使第一个方法返回false，在第二个方法里写我们的验证逻辑
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);  //如果token不为空，执行shrio的登录方法，如果成功，则通过该过滤器，否则的话执行onLoginFailure方法
    }

	//重写createToken方法,主要用于封装我们的登录信息到AuthenticationToken
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String token = getRequestToken(request);
        return new ShiroAuthenticationToken(token);
    }

    /**
     * 登陆失败,直接抛出异常
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        throw new BusinessException(ResultCodeEnums.VAILD_TOKEN,"登陆失败");
    }

    //查看请求中是否携带token
    private String getRequestToken(ServletRequest httpRequest){
        HttpServletRequest request = (HttpServletRequest) httpRequest;
        String token = request.getHeader("Authorization");
        if(token == null){
            token = request.getParameter("token");
        }
        if(token == null){
            throw new BusinessException(ResultCodeEnums.VAILD_TOKEN,"Authorization为空");
        }
        return token;
    }


}
