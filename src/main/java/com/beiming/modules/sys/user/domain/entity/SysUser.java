package com.beiming.modules.sys.user.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 用户表
 *
 * Date 2019-12-23
 */

@Data
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(value = "自增ID")
    private Integer id;

    @ApiModelProperty(value = "用户名(昵称)")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "锁定状态(0--正常   1--锁定)")
    private String lockStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人ID")
    private Integer createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人ID")
    private Integer updateUser;
}