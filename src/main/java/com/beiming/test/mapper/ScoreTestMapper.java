package com.beiming.test.mapper;

import com.beiming.modules.base.mapper.BaseMapper;
import com.beiming.test.domain.ScoreTest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScoreTestMapper extends BaseMapper<ScoreTest> {

    ScoreTest selectByPrimaryKey1(Integer id);
}