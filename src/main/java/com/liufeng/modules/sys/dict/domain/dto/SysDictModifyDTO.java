package com.beiming.modules.sys.dict.domain.dto;

import io.swagger.annotations.ApiModel;;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.*;
import lombok.Data;

@ApiModel(description = "字典表DTO")
@Data
public class SysDictModifyDTO {
    @ApiModelProperty(value = "自增ID")
    private Integer id;

    @ApiModelProperty(value = "code码")
    @Max(value = 20 ,message = "code码最大长度为20")
    @NotBlank(message = "code码不能为空")
    private String code;

    @ApiModelProperty(value = "描述")
    @Max(value = 50 ,message = "描述最大长度为50")
    @NotBlank(message = "描述不能为空")
    private String desc;

    @ApiModelProperty(value = "字典值")
    @Max(value = 50 ,message = "字典值最大长度为50")
    @NotBlank(message = "字典值不能为空")
    private String value;

    @ApiModelProperty(value = "类型")
    @Max(value = 20 ,message = "类型最大长度为20")
    @NotBlank(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "显示顺序")
    @Max(value = 3 ,message = "显示顺序最大长度为3")
    @NotNull(message = "显示顺序不能为空")
    private Integer order;

    @ApiModelProperty(value = "是否显示(0--显示   1--不显示)")
    @Max(value = 1 ,message = "是否显示(0--显示   1--不显示)最大长度为1")
    @NotBlank(message = "是否显示(0--显示   1--不显示)不能为空")
    private String showFlag;
}