package com.beiming.modules.sys.dict.domain.vo;

import io.swagger.annotations.ApiModel;;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(description = "字典表VO")
@Data
public class SysDictVO {
    @ApiModelProperty(value = "自增ID")
    private Integer id;

    @ApiModelProperty(value = "code码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "显示顺序")
    private Integer order;

    @ApiModelProperty(value = "是否显示(0--显示   1--不显示)")
    private String showFlag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;
}