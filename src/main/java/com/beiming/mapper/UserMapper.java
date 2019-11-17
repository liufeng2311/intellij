package com.beiming.mapper;

import com.beiming.domian.entity.User;
import com.beiming.mapper.base.BaseMapper;
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
     * 根据手机号和密码判断用户是否存在
     */
    @Select("select count(1) from user where phone = #{phone} and password = #{password}")
    int getUserByPhoneAndPass(@Param("phone") String phone, @Param("password") String password);
}
