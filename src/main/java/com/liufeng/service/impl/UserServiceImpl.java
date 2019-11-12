package com.liufeng.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.liufeng.common.enums.ResultCodeEnums;
import com.liufeng.common.exception.BusinessException;
import com.liufeng.common.security.JWTToken;
import com.liufeng.common.utils.*;
import com.liufeng.domian.dto.base.BasePageQuery;
import com.liufeng.domian.dto.request.UserLoginRequestDTO;
import com.liufeng.domian.dto.request.UserModifyRequestDTO;
import com.liufeng.domian.dto.request.ChangePassRequestDTO;
import com.liufeng.domian.entity.User;
import com.liufeng.mapper.UserMapper;
import com.liufeng.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void addUser(UserModifyRequestDTO user) {
        existCheck(user.getPhone());
        User target = new User();
        BeanUtils.copyProperties(user, target);
        target.setCreateTime(DateUtils.localDateTime2Date(LocalDateTime.now()));
        target.setCreateUser(user.getUser());
        int count = userMapper.insert(target);
        AssertUtils.sqlResultCheck(count);
    }

    @Override
    public void updateUser(UserModifyRequestDTO user) {
        existCheck(user.getPhone());
        User target = new User();
        BeanUtils.copyProperties(user, target);
        target.setUpdateTime(DateUtils.localDateTime2Date(LocalDateTime.now()));
        target.setUpdateUser(user.getUser());
        int count = userMapper.updateByPrimaryKeySelective(target);
        AssertUtils.sqlResultCheck(count);
    }

    @Override
    public void delUser(Integer id) {
        int count = userMapper.deleteByPrimaryKey(id);
        AssertUtils.sqlResultCheck(count);
    }

    @Override
    public List<User> getAllList(BasePageQuery page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        return userMapper.selectAll();
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public void changePass(ChangePassRequestDTO pass) {
        if(!pass.getPassword().equals(pass.getVerifyPass())){
            throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "两次输入的密码不一致");
        }
        String verity = (String)redisUtils.get(pass.getPhone());  //获取验证码
        if(verity == null){
            throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "验证码以失效，请获取新的验证码");
        }
        if(!pass.getVerifyCode().equals(verity)){
            throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "验证码验证失败");
        }
        User target =new User();
        target.setPassword(EncryptUtils.md5(pass.getPassword()));
        target.setUpdateTime(DateUtils.localDateTime2Date(LocalDateTime.now()));
        target.setUpdateUser(pass.getUser());
        int count = userMapper.updateByPrimaryKeySelective(target);
        AssertUtils.sqlResultCheck(count);
    }

    @Override
    public String sendVerityCode(String phone) {
        String code = CommonsUtils.generateSMSCode();
        redisUtils.addKey(phone,code,5,TimeUnit.SECONDS); //设置过期时间五秒
        return code;
    }

    @Override
    public String login(UserLoginRequestDTO user) {
        User info = userMapper.getUserByPhone(user.getPhone());
        if(info == null && !EncryptUtils.md5(user.getPassword()).equals(info.getPassword())){
            throw new BusinessException(ResultCodeEnums.USER_EXIST, "用户认证失败");
        }
        return JWTToken.createJWT(info.getId().toString(), user.getPhone(), JSON.toJSONString(info));
    }

    //判断手机号是否已存在
    public void existCheck(String phone){
        int select = userMapper.countUserByPhone(phone);
        if(select == 1){
            throw new BusinessException(ResultCodeEnums.BAD_SQL_CHECK, "用户表中已存在");
        }
    }
}
