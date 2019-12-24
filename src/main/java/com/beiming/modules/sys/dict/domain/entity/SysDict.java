package com.beiming.modules.sys.dict.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典表
 *
 * Date 2019-12-23
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_dict")
public class SysDict {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(value = "自增ID")
    private Integer id;

    @ApiModelProperty(value = "code码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "显示顺序")
    private Integer order;

    @ApiModelProperty(value = "是否显示(0--显示   1--不显示)")
    private String showFlag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private Integer updateUser;
}