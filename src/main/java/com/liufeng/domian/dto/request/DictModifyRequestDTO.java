package com.liufeng.domian.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "修改和新增字典表DTO")
public class DictModifyRequestDTO {

    @ApiModelProperty(value = "id不为空时表示修改")
    private Integer id;

    @ApiModelProperty(value = "code码", required = true)
    @NotBlank(message = "code码不能为空")
    private String code;

    @ApiModelProperty(value = "描述", required = true)
    @NotBlank(message = "描述不能为空")
    private String desc;

    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank(message = "操作人不能为空")
    private String user;
}
