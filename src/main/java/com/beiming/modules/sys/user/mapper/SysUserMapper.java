package com.beiming.modules.sys.user.mapper;

import com.beiming.modules.base.mapper.BaseMapper;
import com.beiming.modules.sys.user.domain.entity.SysUser;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据唯一约束判断数据是否已存在
     */
    @Select("select count(1) from sys_user where phone = #{phone}")
    int countUserByPhone(String phone);

    /**
     * 根据手机号获取用户信息
     */
    @Select("select id, phone, username, password, lock_status lockStatus from sys_user where phone = #{phone}")
    SysUser getUserByPhone(String phone);

    /**
     * 根据用户ID、手机号、密码判断用户账号状态
     */
    @Select("select lock_status lockStatus from sys_user where id = #{id} and phone = #{phone} and password = #{password}")
    String getUserByPhoneAndPass(@Param("id") Integer id, @Param("phone") String phone, @Param("password") String password);

    /**
     * 根据用户ID获取用户菜单权限信息
     */
    @Select("select m.permit permit from sys_user_role r left join sys_role_menu rm on r.id = rm.role_id left join sys_menu m on rm.menu_id = m.id where r.user_id = #{userId}")
    Set<String> getUserMenus(Integer userId);
}
