package com.beiming.modules.sys.user.mapper;

import com.beiming.modules.base.mapper.BaseMapper;
import com.beiming.modules.sys.user.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据唯一约束判断数据是否已存在
     */
    @Select("select count(1) from user where phone = #{phone}")
    int countUserByPhone(String phone);

    /**
     * 根据手机号获取用户信息
     */
    @Select("select id, phone, username, password, lock_status lockStatus from user where phone = #{phone}")
    User getUserByPhone(String phone);

    /**
     * 根据用户ID、手机号、密码判断用户账号状态
     */
    @Select("select lock_status lockStatus from user where id = #{id} and phone = #{phone} and password = #{password}")
    String getUserByPhoneAndPass(@Param("id") Integer id, @Param("phone") String phone, @Param("password") String password);
}
