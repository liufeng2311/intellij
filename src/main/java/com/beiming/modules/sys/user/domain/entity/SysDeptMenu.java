package com.beiming.modules.sys.user.domain.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_dept_menu")
public class SysDeptMenu {

    private Integer id; //ID

    private Integer userId; //用户ID

    private Integer deptId; //部门ID

    private Date createTime; //创建时间

    private Integer createUser; //创建人
}
