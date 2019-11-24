package com.beiming.modules.sys.menu.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "菜单保存DTO")
public class SysMenuSaveDTO {

    @ApiModelProperty(value = "ID不为空时表示修改")
    private String id;

    @ApiModelProperty(value = "父级菜单ID", required = true)
    @NotNull(message = "parentId is null")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "name is null")
    @Size(min = 1, max = 50, message = "name length between 1 and 50")
    private String name;

    @ApiModelProperty(value = "菜单对应页面url")
    private String url;

    @ApiModelProperty(value = "菜单所需权限", required = true)
    @NotBlank(message = "permit is null")
    @Size(min = 1, max = 50, message = "permit length between 1 and 50")
    private String permit;

    @ApiModelProperty(value = "菜单图标url", required = true)
    @NotBlank(message = "icon is null")
    @Size(min = 1, max = 50, message = "icon length between 1 and 50")
    private String icon;

    @ApiModelProperty(value = "菜单是否显示", required = true)
    @NotBlank(message = "showFlag is null")
    @Size(min = 1, max = 1, message = "showFlag length between 1 and 50")
    private String showFlag; //是否显示,0表示显示
}
