package com.liufeng.common.constant;

/**
 * 参数验证常数
 */
public class RegexpConstant {

    //密码为8到16位数字和字母的组合
    public final static String PASSWORD_ONE = "^(?=.*[0-9])(?=.*[a-zA-Z])[0-9A-Za-z]{8,16}$";

    //密码为8到16位数字和字母的组合,必须含有大写小写和数字
    public final static String PASSWORD_TWO = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9A-Za-z]{8,16}$";

    //手机号为11位,且第一位为1，第二位为3-9的任意一个
    public final static String PHONE_ONE = "^[1][3-9][0-9]{9}$";

    //四位数字验证码
    public final static String VERITY_NUM_FOUR = "^[0-9]{4}$";

    //六位数字验证码
    public final static String VERITY_NUM_SIX = "^[0-9]{6}$";
}
