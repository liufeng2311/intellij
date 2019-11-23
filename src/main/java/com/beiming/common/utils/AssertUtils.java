package com.beiming.common.utils;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;

/**
 * 断言工具类
 */
public class AssertUtils {

    //当false时抛出异常
    public static void checkFalse(boolean flag, ResultCodeEnums enums, String message) {
        if (!flag) throw new BusinessException(enums, message);
    }

    //当true时抛出异常
    public static void checkTrue(boolean flag, ResultCodeEnums enums, String message) {
        if (flag) throw new BusinessException(enums, message);
    }

    //当大于零时抛出异常
    public static void checkGraterZero(int num, ResultCodeEnums enums, String message) {
        if (num > 0) throw new BusinessException(enums, message);
    }

    //当小于零时抛出异常
    public static void checkLessZero(int num, ResultCodeEnums enums, String message) {
        if (num < 0) throw new BusinessException(enums, message);
    }

    //当等于0时抛出异常
    public static void checkZero(int num, ResultCodeEnums enums, String message) {
        if (num == 0) throw new BusinessException(enums, message);
    }
}
