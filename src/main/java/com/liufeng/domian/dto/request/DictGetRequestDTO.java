package com.liufeng.domian.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liufeng.common.utils.DateUtils;
import com.liufeng.domian.dto.base.BasePageQuery;
import com.liufeng.domian.entity.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true) //生成的方法中包含父类的属性
@ApiModel(description = "根据某一属性查询DTO")
public class DictGetRequestDTO extends BasePageQuery {

    @ApiModelProperty(value = "type值",required = true)
    @NotNull(message = "类型(type)不能为空")
    @Size(min = 1, max = 128, message = "类型(type)长度为1~128")
    private String type;

}
