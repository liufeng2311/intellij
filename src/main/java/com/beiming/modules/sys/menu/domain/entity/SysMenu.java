package com.beiming.modules.sys.menu.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统菜单实体类
 */
@Data
@Builder
@NoArgsConstructor  // @Builder会造成@Data中的无法构造方式失效,需要指定无参构造
@AllArgsConstructor 
@Table(name = "sys_menu")
public class SysMenu {

    @Id
    private Integer id; //菜单ID

    private Integer parentId; //父级菜单ID

    private String name; //菜单显示内容

    private String url; //如果对应的是页面,url为页面地址

    private String permit; //菜单对应的权限

    private String icon; //菜单对应的图标url

    private String showFlag; //是否显示,0表示显示

    @Column(name = "`order`")
    private Integer order; //显示顺序

    private Integer createUser; //创建人

    private Date createTime; //创建时间

    private Integer updateUser; //更新人

    private Date updateTime; //更新时间
}
