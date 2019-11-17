package com.beiming.domian.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 字典表
 * order、desc等字段为mysql关键字,需要用@Column定义别名
 */
@Data
@Table(name = "dict")
public class Dict {

    @Id
    private Integer id; //自增ID

    private String code; //code码

    @Column(name = "`desc`")
    private String desc; //描述

    private String type; //类型

    @Column(name = "`order`")
    private String order; //排序

    private String showFlag; //是否显示

    private Date createTime; //创建时间

    private String createUser; //创建人

    private Date updateTime; //更新时间

    private String updateUser; //更新人
}
