package com.beiming.modules.sys.dict.mapper;

import com.beiming.modules.base.mapper.BaseMapper;
import com.beiming.modules.sys.dict.domain.entity.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 根据类型获取字典数据
     */
    @Select("select id, code, value, `desc`, type from dict where type = #{type} and show_flag = 0")
    List<Dict> getListByType(String type);

    /**
     * 根据唯一约束判断数据是否已存在
     */
    @Select("select count(id) from dict where code = #{code} and type = #{type}")
    int getDictByCodeAndType(@Param("code") String code, @Param("type") String type);
}
