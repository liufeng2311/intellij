package com.liufeng.domian.dto.request;

import com.liufeng.common.constant.RegexpConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "查询用户表DTO、发送验证码DTO")
public class UserGetRequestDTO {

    @ApiModelProperty(value = "手机号", required = true)
    @Pattern(regexp = RegexpConstant.PHONE_ONE, message = "请输入有效手机号")
    private String phone;

}
