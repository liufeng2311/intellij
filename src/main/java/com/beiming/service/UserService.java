package com.beiming.service;

import com.beiming.domian.dto.base.BasePageQuery;
import com.beiming.domian.dto.request.UserLoginRequestDTO;
import com.beiming.domian.dto.request.UserModifyRequestDTO;
import com.beiming.domian.dto.request.ChangePassRequestDTO;
import com.beiming.domian.entity.User;

import java.util.List;

/**
 * 用户表接口
 */
public interface UserService {

    /**
     * 添加用户
     */
    void addUser(UserModifyRequestDTO user);

    /**
     * 更新用户
     */
    void updateUser(UserModifyRequestDTO user);

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
