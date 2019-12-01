package com.beiming.modules.base.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Mapper需继承该接口
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
