package com.beiming.test.domain;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 分数表
 *
 * Author Liufeng
 * Date 2019-12-05
 */

@Data
@Table(name = "score_test")
public class ScoreTest {

    @Id
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "学生ID")
    private Integer stuId;

    @ApiModelProperty(value = "科目ID")
    private Integer courseId;

    @ApiModelProperty(value = "分数")
    private Double score;
}