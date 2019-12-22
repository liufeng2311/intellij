package com.beiming.modules.base.domain;

import com.beiming.common.annotation.param.CheckTimeInterval;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "共用时间类")
@CheckTimeInterval
public class BaseTimeTest {

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
