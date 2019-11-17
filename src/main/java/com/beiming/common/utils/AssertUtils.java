package com.beiming.common.utils;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;

/**
 * 断言工具类
 */
public class AssertUtils {

    public static void sqlResultCheck(int... param) {
        boolean checkFlag = false;
        for (Integer var : param) {
            if (var == 0) {
                checkFlag = true;
                break;
            }
        }
        if (checkFlag) {
            throw new BusinessException(ResultCodeEnums.BAD_SQL_DELETE);
        }
    }

    //为true时抛出异常
    public static void trueCheck(boolean lean, ResultCodeEnums enmus, String message) {
        if (lean) {
            throw new BusinessException(enmus, message);
        }
    }
}
