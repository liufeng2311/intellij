package com.beiming.modules.sys.user.domain.dto;

import com.beiming.common.constant.RegexpConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "用户登录DTO")
public class UserLoginRequestDTO {

    @ApiModelProperty(value = "手机号", required = true)
    @Pattern(regexp = RegexpConstant.PHONE_ONE, message = "请输入有效手机号")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @ApiModelProperty(value = "密码", required = true)
    @Pattern(regexp = RegexpConstant.PASSWORD_ONE,message = "密码为8到16位数字和字母的组合")
    @NotBlank(message = "密码不能为空")
    private String password;

}
