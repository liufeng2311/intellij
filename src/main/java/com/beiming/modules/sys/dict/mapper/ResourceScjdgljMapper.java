package com.beiming.modules.sys.dict.mapper;

import com.beiming.modules.sys.dict.domain.entity.ResourceScjdglj;
import com.beiming.modules.sys.dict.domain.entity.ResourceScjdgljExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourceScjdgljMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    long countByExample(ResourceScjdgljExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    int deleteByExample(ResourceScjdgljExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    int insert(ResourceScjdglj record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    int insertSelective(ResourceScjdglj record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    List<ResourceScjdglj> selectByExample(ResourceScjdgljExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    ResourceScjdglj selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    int updateByExampleSelective(@Param("record") ResourceScjdglj record, @Param("example") ResourceScjdgljExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    int updateByExample(@Param("record") ResourceScjdglj record, @Param("example") ResourceScjdgljExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    int updateByPrimaryKeySelective(ResourceScjdglj record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resource_scjdglj
     *
     * @mbg.generated Wed Apr 22 15:20:05 CST 2020
     */
    int updateByPrimaryKey(ResourceScjdglj record);
}