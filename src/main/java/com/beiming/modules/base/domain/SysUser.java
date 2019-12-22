package com.beiming.modules.base.domain;

import lombok.Data;

/**
 * 定义共用的用户信息
 */
@Data
public class SysUser {

    private String id; //用户ID

    private String username; //用户名

    private String phone; //用户手机
}
