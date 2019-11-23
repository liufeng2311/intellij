package com.beiming.modules.sys.user.domain.dto;

import com.beiming.common.constant.RegexpConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "用户更新信息表")
public class UserModifyRequestDTO {

    @ApiModelProperty(value = "id不为空时表示修改")
    private Integer id;

    @ApiModelProperty(value = "phone", required = true)
    @Pattern(regexp = RegexpConstant.PHONE_ONE, message = "请输入有效手机号")
    private String phone;

    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @ApiModelProperty(value = "密码(新增用户时为必填)")
    private String password;

    @ApiModelProperty(value = "用户状态", required = true)
    @NotBlank(message = "用户状态不能为空")
    @Size(max = 128, message = "用户状态长度为1")
    private String lockStatus;

    @ApiModelProperty(value = "操作人", required = true)
    @NotBlank(message = "操作人不能为空")
    @Size(max = 128, message = "操作人长度为128")
    private String user;
}
