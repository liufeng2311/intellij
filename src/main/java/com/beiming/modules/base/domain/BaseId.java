package com.beiming.modules.base.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "共用ID类")
public class BaseId {

    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID属性不能为空")
    @Size(max = 32, message = "ID长度为32")
    private Integer id;
}
