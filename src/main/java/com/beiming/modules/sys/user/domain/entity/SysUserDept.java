package com.beiming.modules.sys.user.domain.entity;


import lombok.Data;

import javax.persistence.Table;

/**
 * 用户部门表
 */
@Data
@Table(name = "sys_user_dept")
public class SysUserDept {

    private Integer id; //ID

    private Integer deptId; //部门ID

    private Integer userId; //用户ID

    private Data createTime; //创建时间

    private Integer createUser; //创建人
}
