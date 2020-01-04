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
 * 系统菜单表
 * <p>
 * Date 2019-12-23
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sys_menu")
public class SysMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "父级菜单ID,一级菜单为0")
    private Integer parentId;

    @ApiModelProperty(value = "菜单描述")
    private String name;

    @ApiModelProperty(value = "菜单对应页面的url")
    private String url;

    @ApiModelProperty(value = "权限")
    private String permit;

    @ApiModelProperty(value = "图表url")
    private String icon;

    @ApiModelProperty(value = "是否显示(0-显示,1-不显示)")
    private String showFlag;

    @ApiModelProperty(value = "显示顺序")
    private Byte order;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;
}