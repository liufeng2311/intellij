package com.beiming.modules.base.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "共用分页类")
public class BaseSort {

    @ApiModelProperty(value = "排序规则(asc-升序,desc-降序)",required = true)
    @NotBlank(message = "排序规则属性不能为空")
    private String sort;

    @ApiModelProperty(value = "排序字段",required = true)
    @NotBlank(message = "排序字段属性不能为空")
    private String filed;
}
