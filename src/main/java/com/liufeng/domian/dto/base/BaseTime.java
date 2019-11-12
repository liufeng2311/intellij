package com.liufeng.domian.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ApiModel(description = "共用时间类")
public class BaseTime {

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy")   //接受前台传过来的时间格式
    @DateTimeFormat(pattern = "yyyy-MM-dd")  //返回给前台的格式
    private Data startTime;

    @JsonFormat(pattern = "yyyy")   //接受前台传过来的时间格式
    @DateTimeFormat(pattern = "yyyy-MM-dd")  //返回给前台的格式
    @ApiModelProperty(value = "结束时间")
    private Data endTime;
}
