package com.liufeng.domian.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "修改密码DTO")
public class ChangePassRequestDTO {

    @ApiModelProperty(value = "手机号" ,required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "验证码" ,required = true)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    @ApiModelProperty(value = "密码" ,required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "确认密码" ,required = true)
    @NotBlank(message = "确认密码不能为空")
    private String verifyPass;

    @ApiModelProperty(value = "操作人" ,required = true)
    @NotBlank(message = "操作人不能为空")
    private String user;
}
