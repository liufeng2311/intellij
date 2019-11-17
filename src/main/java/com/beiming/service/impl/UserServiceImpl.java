package com.beiming.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.beiming.common.constant.RedisKeyConstant;
import com.beiming.common.constant.RegexpConstant;
import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;
import com.beiming.common.security.JWTToken;
import com.beiming.common.utils.*;
import com.beiming.domian.dto.base.BasePageQuery;
import com.beiming.domian.dto.request.ChangePassRequestDTO;
import com.beiming.domian.dto.request.UserLoginRequestDTO;
import com.beiming.domian.dto.request.UserModifyRequestDTO;
import com.beiming.domian.entity.User;
import com.beiming.mapper.UserMapper;
import com.beiming.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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
        if(Pattern.compile(RegexpConstant.PASSWORD_ONE).matcher(user.getPassword()).matches()){
            target.setPassword(EncryptUtils.md5(user.getPassword()));
        }else {
            throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER,"密码为8到16位数字和字母的组合");
        }
        int count = userMapper.insert(target);
        AssertUtils.sqlResultCheck(count);
    }

    @Override
    public void updateUser(UserModifyRequestDTO user) {
        existCheck(user.getPhone());
        User target = new User();
        target.setPassword(null);
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
        String verity = (String)redisUtils.get(RedisKeyConstant.SEND_VERITY_CODE_KEY_PREFIX + pass.getPhone());
        if(verity == null || !pass.getVerifyCode().equals(verity)){
            throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "请输入有效验证码");
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
        redisUtils.addKey(RedisKeyConstant.SEND_VERITY_CODE_KEY_PREFIX + phone,code,5,TimeUnit.SECONDS); //设置过期时间五秒
        return code;
    }

    @Override
    public String login(UserLoginRequestDTO user) {
        User info = userMapper.getUserByPhone(user.getPhone());
        if(info == null || !EncryptUtils.md5(user.getPassword()).equals(info.getPassword())){
            throw new BusinessException(ResultCodeEnums.USER_EXCEPTION, "用户认证失败");
        }
        return JWTToken.createJWT(info.getId().toString(), user.getPhone(), JSON.toJSONString(info));
    }

    //判断手机号是否已存在
    public void existCheck(String phone){
        int select = userMapper.countUserByPhone(phone);
        if(select == 1){
            throw new BusinessException(ResultCodeEnums.USER_EXCEPTION, "用户表中已存在");
        }
    }
}
