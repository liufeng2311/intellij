package com.beiming.modules.sys.menu.domain.vo;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@ApiModel(description = "菜单VO")
public class SysMenuVO {

    private Integer id; //菜单ID

    private Integer parentId; //父级菜单ID

    private String name; //菜单显示内容

    private String url; //如果对应的是页面,url为页面地址

    private String permit; //菜单对应的权限

    private String icon; //菜单对应的图标url

    private Integer order; //显示顺序

    private List<SysMenuVO> child; //子菜单集合
}
