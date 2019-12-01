package com.beiming.modules.sys.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.beiming.common.constant.RedisKeyConstant;
import com.beiming.common.constant.RegexpConstant;
import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;
import com.beiming.common.security.JWTToken;
import com.beiming.common.utils.AssertUtils;
import com.beiming.common.utils.CommonsUtils;
import com.beiming.common.utils.EncryptUtils;
import com.beiming.common.utils.RedisUtils;
import com.beiming.modules.base.domain.BasePageQuery;
import com.beiming.modules.sys.user.domain.dto.ChangePassRequestDTO;
import com.beiming.modules.sys.user.domain.dto.UserLoginRequestDTO;
import com.beiming.modules.sys.user.domain.dto.UserModifyRequestDTO;
import com.beiming.modules.sys.user.domain.entity.SysUser;
import com.beiming.modules.sys.user.mapper.SysUserMapper;
import com.beiming.modules.sys.user.service.ISysUserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void addUser(Integer uid, UserModifyRequestDTO user) {
        existCheck(user.getPhone());
        SysUser target = new SysUser();
        BeanUtils.copyProperties(user, target);
        target.setCreateTime(new Date());
        target.setCreateUser(uid);
        if(Pattern.compile(RegexpConstant.PASSWORD_ONE).matcher(user.getPassword()).matches()){
            target.setPassword(EncryptUtils.md5(user.getPassword()));
        }else {
            throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER,"密码为8到16位数字和字母的组合");
        }
        int insert = sysUserMapper.insert(target);
        AssertUtils.checkZero(insert, ResultCodeEnums.BAD_SQL_CHECK, "新增用户表数据失败");
    }

    @Override
    public void updateUser(Integer uid, UserModifyRequestDTO user) {
        existCheck(user.getPhone());
        SysUser target = new SysUser();
        target.setPassword(null);
        BeanUtils.copyProperties(user, target);
        target.setUpdateTime(new Date());
        target.setUpdateUser(uid);
        int update = sysUserMapper.updateByPrimaryKeySelective(target);
        AssertUtils.checkZero(update, ResultCodeEnums.BAD_SQL_CHECK, "修改用户表数据失败");
    }

    @Override
    public void delUser(Integer id) {
        int delete = sysUserMapper.deleteByPrimaryKey(id);
        AssertUtils.checkZero(delete, ResultCodeEnums.BAD_SQL_CHECK, "删除用户表数据失败");
    }

    @Override
    public List<SysUser> getAllList(BasePageQuery page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        return sysUserMapper.selectAll();
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        return sysUserMapper.getUserByPhone(phone);
    }

    @Override
    public SysUser getUserById(Integer id) {
        return sysUserMapper.selectByPrimaryKey(id);
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
        SysUser target =new SysUser();
        target.setPassword(EncryptUtils.md5(pass.getPassword()));
        target.setUpdateTime(new Date());
        int count = sysUserMapper.updateByPrimaryKeySelective(target);
        AssertUtils.checkZero(count, ResultCodeEnums.BAD_SQL_CHECK, "修改密码失败");
    }

    @Override
    public String sendVerityCode(String phone) {
        String code = CommonsUtils.generateSMSCode();
        redisUtils.addKey(RedisKeyConstant.SEND_VERITY_CODE_KEY_PREFIX + phone,code,5,TimeUnit.SECONDS); //设置过期时间五秒
        return code;
    }

    @Override
    public String login(UserLoginRequestDTO user) {
        SysUser info = sysUserMapper.getUserByPhone(user.getPhone());
        if(info == null || !EncryptUtils.md5(user.getPassword()).equals(info.getPassword())){
            throw new BusinessException(ResultCodeEnums.USER_EXCEPTION, "用户认证失败");
        }
        return JWTToken.createJWT(info.getId().toString(), user.getPhone(), JSON.toJSONString(info));
    }

    //判断手机号是否已存在
    public void existCheck(String phone){
        int select = sysUserMapper.countUserByPhone(phone);
        if(select == 1){
            throw new BusinessException(ResultCodeEnums.USER_EXCEPTION, "用户表中已存在");
        }
    }
}
