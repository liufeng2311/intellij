package com.liufeng.domian.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "共用ID类")
public class BaseId {

    @ApiModelProperty(value = "ID",required = true)
    @NotNull(message = "ID属性不能为空")
    private Integer id;
}
