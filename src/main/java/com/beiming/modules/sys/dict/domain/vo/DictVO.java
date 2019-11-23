package com.beiming.modules.sys.dict.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "字典类DTO")
public class DictVO {

    @ApiModelProperty(value = "code码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "类型")
    private String type;
}
