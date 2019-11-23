package com.beiming.modules.sys.dict.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "修改/新增字典表DTO")
public class DictModifyDTO {

    @ApiModelProperty(value = "ID不为空时表示修改")
    private Integer id;

    @ApiModelProperty(value = "字典码", required = true)
    @NotBlank(message = "code is null")
    @Size(min = 1, max = 20, message = "code length between 1 and 20")
    private String code;

    @ApiModelProperty(value = "字典值", required = true)
    @NotBlank(message = "value is null")
    @Size(min = 1, max = 20, message = "value length between 1 and 20")
    private String value;

    @ApiModelProperty(value = "字典描述", required = true)
    @NotBlank(message = "desc is null")
    @Size(min = 1, max = 50, message = "desc length between 1 and 50")
    private String desc;

    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "type is null")
    @Size(min = 1, max = 20, message = "type length between 1 and 20")
    private String type;

    @ApiModelProperty(value = "显示标志", required = true)
    @NotBlank(message = "showFlag is null")
    @Size(min = 1, max = 1, message = "showFlag length must be 1")
    private String showFlag;
}
