package com.beiming.test.mapper;

import com.beiming.modules.base.mapper.BaseMapper;
import com.beiming.test.domain.ScoreTest;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ScoreTestMapper extends BaseMapper<ScoreTest> {

    @MapKey("id")
    Map<String,ScoreTest> selectByPrimaryKey1(Integer id);
}