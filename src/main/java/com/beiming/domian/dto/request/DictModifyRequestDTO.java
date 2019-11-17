package com.beiming.domian.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "修改和新增字典表DTO")
public class DictModifyRequestDTO {

    @ApiModelProperty(value = "id不为空时表示修改")
    private Integer id;

    @ApiModelProperty(value = "code码", required = true)
    @NotBlank(message = "code码不能为空")
    @Size(max = 128, message = "code码长度为128")
    private String code;

    @ApiModelProperty(value = "描述", required = true)
    @NotBlank(message = "描述不能为空")
    @Size(max = 128, message = "描述长度为128")
    private String desc;

    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "类型不能为空")
    @Size(max = 128, message = "类型长度为128")
    private String type;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank(message = "操作人不能为空")
    @Size(max = 128, message = "操作人长度为128")
    private String user;
}
