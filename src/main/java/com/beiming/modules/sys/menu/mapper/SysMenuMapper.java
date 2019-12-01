package com.beiming.modules.sys.menu.mapper;


import com.beiming.modules.base.mapper.BaseMapper;
import com.beiming.modules.sys.menu.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 批量删除
     */
    int delBatch(List<Integer> list);

    /**
     * 批量更新
     */
    int updateBatch(@Param("flag") String showFlag, @Param("list") List<Integer> list);

    /**
     * 获取最大序号
     *
     * @param id
     */
    @Select("select IFNULL(max(`order`),0) from sys_menu where parent_id = #{id}")
    int maxOrder(Integer id);
}
