package com.beiming.modules.sys.user.service.impl;


import com.beiming.modules.base.domain.BasePageQuery;
import com.beiming.modules.sys.user.domain.dto.ChangePassRequestDTO;
import com.beiming.modules.sys.user.domain.dto.UserLoginRequestDTO;
import com.beiming.modules.sys.user.domain.dto.UserModifyRequestDTO;
import com.beiming.modules.sys.user.domain.entity.User;

import java.util.List;

/**
 * 用户表接口
 */
public interface IUserService {

    /**
     * 添加用户
     */
    void addUser(Integer uid, UserModifyRequestDTO user);

    /**
     * 更新用户
     */
    void updateUser(Integer uid, UserModifyRequestDTO user);

    /**
     * 删除用户
     */
    void delUser(Integer id);

    /**
     * 获取用户列表
     */
    List<User> getAllList(BasePageQuery page);

    /**
     * 获取用户信息
     */
    User getUserByPhone(String phone);

    /**
     * 获取用户信息
     */
    User getUserById(Integer id);

    /**
     * 修改密码
     */
    void changePass(ChangePassRequestDTO pass);

    /**
     * 发送验证码
     */

    String sendVerityCode(String phone);

    /**
     * 用户登录
     */
    String login(UserLoginRequestDTO user);
}
