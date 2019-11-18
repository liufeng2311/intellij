package com.beiming.common.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义Shiro过滤器
 * 先判断是否放行,若放行executeLogin()登录方法
 * 登陆中会执行createToken()方法生成token
 * 成功则放行
 * 失败执行onLoginFailure方法
 * 全局的异常捕获只能捕捉Controller层的异常,所以这里不能使用自定义异常
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
        return executeLogin(request, response);
    }

	//重写createToken方法,主要用于封装我们的登录信息到AuthenticationToken
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String token = getRequestToken(request);
        return new ShiroAuthenticationToken(token);
    }

    /**
     * 登陆失败,通过Response输出信息
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return false;
    }

    //查看请求中是否携带token
    private String getRequestToken(ServletRequest httpRequest){
        HttpServletRequest request = (HttpServletRequest) httpRequest;
        String token = request.getHeader("Authorization");
        if(token == null){
            token = request.getParameter("Authorization");
        }
        if(token == null){
            throw new BusinessException(ResultCodeEnums.VAILD_TOKEN,"Authorization is null");
        }
        return token;
    }


}
