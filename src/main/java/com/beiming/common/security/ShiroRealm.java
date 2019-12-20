package com.beiming.common.security;

import com.alibaba.fastjson.JSON;
import com.beiming.common.utils.RedisUtils;
import com.beiming.modules.sys.user.domain.entity.SysUser;
import com.beiming.modules.sys.user.mapper.SysUserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 设置用户权限与身份信息
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    RedisUtils RedisUtils;

    //Shiro框架会对验证封装类进行判断,
    //当我们重写了AuthenticationToken时，需要重新该方法
    //默认的实现类型为UsernamePasswordToken,该方法会判断我们的自定义的token是否属于UsernamePasswordToken
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroAuthenticationToken;
    }

    //权限认证,PrincipalCollection参数的信息是在账号认证完之后封装的
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        //设置用户权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> menus = sysUserMapper.getUserMenus(user.getId());
        info.setStringPermissions(menus);
        return info;
    }

    //账号认证
    //该处的逻辑为根据输入的用户名获取数据库中的密码,并将数据库里的信息封装到SimpleAuthenticationInfo里
    //然后与输入的密码对比,此处我们我们通过解析token自己进行判断,如果判断成功，返回的信息里封装输入的用户名密码,确保框架里的
    //判断为真
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal(); //获取需要认证的用户信息
        SysUser info = JSON.parseObject(JWTToken.parseJWT(token).getSubject(), SysUser.class); //解析token,取得用户真实信息
        String lockStatus = sysUserMapper.getUserByPhoneAndPass(info.getId(), info.getPhone(), info.getPassword()); //获取用户账号锁定信息
        if (!StringUtils.isEmpty(lockStatus) && lockStatus.equals("1")) {
            throw new LockedAccountException("密码已被修改或用户已被锁定");
        }
        //SimpleAuthenticationInfo第二个参数为密码,后期认证只使用密码验证
        //第一个参数可通过SecurityUtils.getSubject().getPrincipal()获取,一般用于存用户信息
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(info, token, getName());
        return auth;
    }
}
