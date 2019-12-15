package com.beiming.modules.sys.dept.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TestDTO {

    @NotNull(groups = {}, message = "ID不能为空")
    private Integer id;
}
