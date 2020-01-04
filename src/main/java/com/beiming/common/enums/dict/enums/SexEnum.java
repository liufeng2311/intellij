package com.beiming.common.enums.dict.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)  //枚举默认输出为name值,此处我们设置为输出具体内容
public enum SexEnum {
    ONE("1", "男"),
    TWO("2", "女");
    private String code;

    private String value;

    SexEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static SexEnum getSexEnumByCode(String code) {
        for (SexEnum sex : values()) {
            if (sex.getCode().equals(code)) {
                return sex;
            }
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}