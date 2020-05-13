package com.beiming.modules.sys.resources.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "resource_weijianwei")
public class resourceWeijianwei {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "组织机构")
    private String organization;

    @ApiModelProperty(value = "科室")
    private String department;

    @ApiModelProperty(value = "人员编号")
    private String personNumber;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "身份证证件号码")
    private String idCard;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "参加工作日期")
    private String workTime;

    @ApiModelProperty(value = "办公室电话")
    private String officePhone;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "证书编码")
    private String certificateEncoding;

    @ApiModelProperty(value = "在位标志")
    private String workFlag;

    @ApiModelProperty(value = "从事专业类别")
    private String specializedType;

    @ApiModelProperty(value = "医师执业类别")
    private String physicianType;

    @ApiModelProperty(value = "管理职务")
    private String managementPosition;

    @ApiModelProperty(value = "专业技术资格")
    private String specialtySkillZg;

    @ApiModelProperty(value = "专业技术职务")
    private String specialtySkillZw;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "学位")
    private String educationalLevel;

    @ApiModelProperty(value = "专业")
    private String specialized;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;
}