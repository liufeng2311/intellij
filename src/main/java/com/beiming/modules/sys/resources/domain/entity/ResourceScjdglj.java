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
@Table(name = "resource_scjdglj")
public class ResourceScjdglj {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "注册号")
    private String registrationNumber;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "企业类型种类")
    private String companyTypeCode;

    @ApiModelProperty(value = "企业类型名称")
    private String companyTypeName;

    @ApiModelProperty(value = "法定代表人")
    private String legalPerson;

    @ApiModelProperty(value = "政治面貌")
    private String politicalStatus;

    @ApiModelProperty(value = "经营地址")
    private String businessAddress;

    @ApiModelProperty(value = "注册资本")
    private String registeredCapital;

    @ApiModelProperty(value = "资本类型")
    private String capitalType;

    @ApiModelProperty(value = "注册资本折万美元")
    private String registeredCapitalDollar;

    @ApiModelProperty(value = "实收资本")
    private String paidInCapital;

    @ApiModelProperty(value = "实收资本折万美元")
    private String paidInCapitalDollar;

    @ApiModelProperty(value = "投资总额")
    private String totalInvestment;

    @ApiModelProperty(value = "投资总额折万美元")
    private String totalInvestmentDollar;

    @ApiModelProperty(value = "创建日期")
    private String createTimes;

    @ApiModelProperty(value = "经营期限起")
    private String operatingBeginTime;

    @ApiModelProperty(value = "经营期限止")
    private String operatingEndTime;

    @ApiModelProperty(value = "状态")
    private String statu;

    @ApiModelProperty(value = "许可经营项目")
    private String licenseProject;

    @ApiModelProperty(value = "经营类别")
    private String licenseType;

    @ApiModelProperty(value = "管辖单位")
    private String policeUnit;

    @ApiModelProperty(value = "主管辖单位")
    private String mainPoliceUnit;

    @ApiModelProperty(value = "登记机关")
    private String registerAuthority;

    @ApiModelProperty(value = "主登记机关")
    private String mainRegisterAuthority;

    @ApiModelProperty(value = "行业类型")
    private String businessType;

    @ApiModelProperty(value = "从业人数")
    private String businessNumber;

    @ApiModelProperty(value = "组成形式")
    private String organizationForm;

    @ApiModelProperty(value = "证件编号")
    private String idCard;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;

    @ApiModelProperty(value = "是否已导出(0-未导入,1-已导入)")
    private String flag;
}