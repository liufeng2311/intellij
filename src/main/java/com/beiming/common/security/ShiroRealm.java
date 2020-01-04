package com.beiming.common.security;

import com.beiming.modules.sys.user.domain.entity.SysUser;
import com.beiming.modules.sys.user.mapper.SysUserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.Date;
import java.util.Set;

/**
 * 设置用户权限与身份信息
 * 重写supports方法,支持我们的token
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroAuthenticationToken;
    }

    //权限认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = sysUserMapper.getUserRoles(user.getId());
        Set<String> menus = sysUserMapper.getUserMenus(user.getId());
        info.setRoles(roles);
        info.setStringPermissions(menus);
        return info;
    }

    //账号认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        SysUser user = sysUserMapper.selectOne(SysUser.builder().token(token).build());
        String lockStatus = user.getLockStatus();
        if (lockStatus.equals("1")) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        if (user.getExpireTime().before(new Date())) {
            throw new ExpiredCredentialsException("登录凭证过期,请重新登录");
        }
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(user, token, getName());
        return auth;
    }
}
