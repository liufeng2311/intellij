package com.beiming.domian.dto.request;

import com.beiming.common.constant.RegexpConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "修改密码DTO")
public class ChangePassRequestDTO {

    @ApiModelProperty(value = "手机号" ,required = true)
    @Pattern(regexp = RegexpConstant.PHONE_ONE, message = "请输入有效手机号")
    private String phone;

    @ApiModelProperty(value = "验证码" ,required = true)
    @Pattern(regexp = RegexpConstant.VERITY_NUM_SIX, message = "验证码为六位数字")
    private String verifyCode;

    @ApiModelProperty(value = "密码" ,required = true)
    @Pattern(regexp = RegexpConstant.PASSWORD_ONE, message = "密码为8到16位数字和字母的组合")
    private String password;

    @ApiModelProperty(value = "确认密码" ,required = true)
    @Pattern(regexp = RegexpConstant.PASSWORD_ONE, message = "密码为8到16位数字和字母的组合")
    private String verifyPass;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank(message = "操作人不能为空")
    @Size(max = 128, message = "操作人最多128个字符")
    private String user;
}
