package com.beiming.modules.base.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "共用分页类")
public class BasePageQuery {

    @ApiModelProperty(value = "显示第几页")
    @NotNull(message = "pageNum is null")
    @Size(min = 1, message = "pageNum must be greater than zero")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示多少数据")
    @NotNull(message = "pageSize is null")
    @Size(min = 1, message = "pageSize must be greater than zero")
    private Integer pageSize;

}
