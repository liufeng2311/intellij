package com.beiming.domian.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "字典类DTO")
public class DictResponseDTO {

    @ApiModelProperty(value = "code码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "类型")
    private String type;
}
