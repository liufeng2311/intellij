package com.beiming.domian.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "字典类一对多")
public class DictResponseListDTO {

    @ApiModelProperty(value = "type")
    private String type;

    @ApiModelProperty(value = "type对应的数据集合")
    private List<String> list;
}
