package com.liufeng.common.utils;

import com.liufeng.common.enums.ResultCodeEnums;
import com.liufeng.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密出来的长度是32位
 */
@Slf4j
public final class EncryptUtils {

    /**
     * md5加密
     */
    public static String md5(String inputText) {
        return encrypt(inputText);
    }

    /**
     * md5或者sha-1加密
     */
    private static String encrypt(String inputText) {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new BusinessException(ResultCodeEnums.VAILD_PARAMETER, "加密内容不能为空");
        }
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance("md5");
            m.update(inputText.getBytes("UTF8"));
            byte s[] = m.digest();
            encryptText = hex(s);
        } catch (NoSuchAlgorithmException e) {
            log.error("Encrypt encrypt error {}", e);
        } catch (UnsupportedEncodingException e) {
            log.error("Encrypt encrypt error {}", e);
        }
        return encryptText;
    }

    /**
     * 返回十六进制字符串
     * 该方法的左右是减少占用空间
     */
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
}
