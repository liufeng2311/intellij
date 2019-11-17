package com.liufeng.domian.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "共用分页类")
public class BasePageQuery {

    @ApiModelProperty(value = "显示第几页")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示多少数据")
    private Integer pageSize;

}
