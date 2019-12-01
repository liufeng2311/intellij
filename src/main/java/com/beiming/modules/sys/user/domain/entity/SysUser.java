package com.beiming.modules.sys.user.domain.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Data
@Table(name = "user")
public class SysUser implements Serializable {

    private Integer id; //自增ID

    private String phone; //手机号

    private String username; //用户名

    private String password; //密码

    private String lockStatus; //用户状态(0--正常,1--锁定)

    private Date createTime; //创建时间

    private Integer createUser; //创建人

    private Date updateTime; //更新时间

    private Integer updateUser; //更新人
}
