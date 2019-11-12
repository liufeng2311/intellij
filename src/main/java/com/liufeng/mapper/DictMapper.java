package com.liufeng.mapper;

import com.liufeng.domian.entity.Dict;
import com.liufeng.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 根据类型获取字典数据
     */
    @Select("select id, code, `desc`,type from dict where type = #{type}")
    List<Dict> getAllListByType(String type);

    /**
     * 根据唯一约束判断数据是否已存在
     */
    @Select("select count(1) from dict where code = #{code} and type = #{type}")
    int getDictByCodeAndType(@Param("code") String code, @Param("type") String type);
}
