package com.beiming.common.utils;

import java.util.regex.Pattern;

/**
 * 正则工具类
 */

public class PatternUtils {

    /**
     * 验证数据是否符合指定的正则式
     *
     * @param regular 正则式
     * @param pattern 需要验证的字符
     * @return
     */
    public static boolean compile(String regular, String pattern) {
        return Pattern.compile(regular)
                .matcher(pattern)
                .matches();
    }
}
