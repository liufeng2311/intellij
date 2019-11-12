package com.liufeng.domian.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "用户登录DTO")
public class UserLoginRequestDTO {

    @ApiModelProperty(value = "phone", required = true)
    @NotBlank(message = "phone不能为空")
    private String phone;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
