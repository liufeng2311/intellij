package com.beiming.modules.sys.user.domain.entity;

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
 * 部门表
 *
 * Date 2019-12-23
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @ApiModelProperty(value = "角色权限")
    private String permit;

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