package com.liufeng.common.security;

import com.alibaba.fastjson.JSON;
import com.liufeng.common.constant.RedisKeyConstant;
import com.liufeng.common.enums.ResultCodeEnums;
import com.liufeng.common.exception.BusinessException;
import com.liufeng.common.utils.EncryptUtils;
import com.liufeng.common.utils.RedisUtils;
import com.liufeng.domian.entity.User;
import com.liufeng.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 设置用户权限与身份信息
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;

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
        String username = principalCollection.getPrimaryPrincipal().toString();
        //通过数据库查询权限信息
        //用户权限列表
        Set<String> permsSet = new HashSet<String>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    //账号认证
    //该处的逻辑为根据输入的用户名获取数据库中的密码,并将数据库里的信息封装到SimpleAuthenticationInfo里
    //然后与输入的密码对比,此处我们我们通过解析token自己进行判断,如果判断成功，返回的信息里封装输入的用户名密码,确保框架里的
    //判断为真
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        String token = (String) authenticationToken.getPrincipal(); //获取token
        User info = JSON.parseObject(JWTToken.parseJWT(token).getSubject(), User.class); //获取用户信息
        if (RedisUtils.get(RedisKeyConstant.SHIRO_KEY_PREFIX + info.getPhone()) != null) {
            info = (User) RedisUtils.get(RedisKeyConstant.SHIRO_KEY_PREFIX + info.getPhone());
        } else {
            int count = userMapper.getUserByPhoneAndPass(info.getPhone(), info.getPassword());//数据库查询验证,此处我们没有使用密码验证，若使用用户名密码验证,要对密码加密
            if (count == 1) {
                RedisUtils.addKey(RedisKeyConstant.SHIRO_KEY_PREFIX + info.getPhone(), info, 5, TimeUnit.SECONDS);
            } else {
                throw new BusinessException(ResultCodeEnums.VAILD_TOKEN, "用户认证失败");
            }
        }

        //SimpleAuthenticationInfo为权限认证的参数,封装的是期望的用户名对应的密码,为我们接收到的token
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(token, token, getName());
        return auth;
    }
}
