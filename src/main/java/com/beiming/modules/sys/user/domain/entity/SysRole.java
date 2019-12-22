package com.beiming.modules.sys.user.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 部门表
 *
 * Author Liufeng
 * Date 2019-12-21
 */

@Data
@Table(name = "sys_role")
public class SysRole {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "父角色ID")
    private Integer parentId;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer order;

    @ApiModelProperty(value = "删除标志(0表示正常,1表示删除)")
    private String delFlag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    private Integer updateUser;
}