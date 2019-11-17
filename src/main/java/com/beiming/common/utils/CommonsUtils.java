package com.beiming.common.utils;

import java.util.Random;
import java.util.UUID;

public class CommonsUtils {

    private static final int RANDOM_MIN = 100000;
    private static final int RANDOM_MAX = 900000;

    /**
     * 获取一个不包含'-'符号的32位长度的UUID
     */
    public static String get32BitUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 验证码规则：6位有效数字
     */
    public static String generateSMSCode() {
        Random r = new Random();
        int code = RANDOM_MIN + r.nextInt(RANDOM_MAX);
        return String.valueOf(code);
    }

    /**
     * 通过身份证号获取性别
     *
     * @return 男/女
     */
    public static String getSexByIdCard(String idCard) {
        String result = "";
        try {

            idCard = idCard.trim();
            int idxSexStart = 16;
            // 如果是15位的证件号码
            if (idCard.length() == 15) {
                idxSexStart = 14;
            }
            // 性别
            String idxSexStr = idCard.substring(idxSexStart, idxSexStart + 1);
            int idxSex = Integer.parseInt(idxSexStr) % 2;
            result = (idxSex == 1) ? "男" : "女";
        } catch (Exception e) {

        }
        return result;
    }


}

