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
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true) //生成的方法中包含父类的属性
@ApiModel(description = "查询字典表DTO")
public class DictGetRequestDTO extends BasePageQuery {

    @ApiModelProperty(value = "字典表type值",required = true)
    @NotNull(message = "字典表类型属性不能为空")
    @Max(value = 128, message = "字典表type长度为128")
    private String type;

}
