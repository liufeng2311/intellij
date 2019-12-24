package com.beiming.modules.sys.user.mapper;

import com.beiming.modules.base.mapper.BaseMapper;
import com.beiming.modules.sys.user.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户角色信息
     */
    Set<String> getUserRoles(Integer id);

    /**
     * 获取用户菜单信息
     */
    Set<String> getUserMenus(Integer id);
}