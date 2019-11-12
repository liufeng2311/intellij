package com.liufeng.domian.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "用户更新信息表")
public class UserModifyRequestDTO {

    @ApiModelProperty(value = "id不为空时表示修改")
    private Integer id;

    @ApiModelProperty(value = "phone", required = true)
    @NotBlank(message = "phone不能为空")
    private String phone;

    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户状态", required = true)
    @NotBlank(message = "用户状态不能为空")
    private String lockStatus;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank(message = "操作人不能为空")
    private String user;
}
