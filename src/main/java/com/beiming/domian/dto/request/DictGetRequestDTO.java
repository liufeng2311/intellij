package com.beiming.domian.dto.request;

import com.beiming.domian.dto.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true) //生成的方法中包含父类的属性
@ApiModel(description = "根据某一属性查询DTO")
public class DictGetRequestDTO extends BasePageQuery {

    @ApiModelProperty(value = "type值",required = true)
    @NotNull(message = "类型(type)不能为空")
    @Size(min = 1, max = 128, message = "类型(type)长度为1~128")
    private String type;

}
