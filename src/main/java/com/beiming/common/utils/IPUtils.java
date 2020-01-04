package com.beiming.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取用户IP
 * 我们通过request.getRemoteAddr()可以获取直接访问我们服务的IP地址，改地址不可伪造,有代理服务器时，获取的是代理服务器的地址
 * 代理服务器转发时会通过自己定义Header字段来记录IP链，以逗号分隔，第一个则为真实的IP地址
 * 所以获取IP时，我们要先判断是否为代理服务器,
 * 存在代理服务器时，获取的IP链可能是伪造的，不一定是真实的地址
 */
@Slf4j
public class IPUtils {

    private static final String LOCAL_IP = "127.0.0.1"; //本地IP
    private static final String DEFAULT_IP = "0:0:0:0:0:0:0:1";  //本机ipv6地址，自己访问自己服务可能得到的是该IP
    public static final int DEFAULT_IP_LENGTH = 15;//默认IP地址长度

    public static String getRealAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for"); //squid代理服务器

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP"); //Apache代理服务器
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP"); //WebLogic代理服务器
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP"); //某些代理服务器
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP"); //nginx代理服务器
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr(); //获取直接连接本服务的IP
            if (LOCAL_IP.equals(ip) || DEFAULT_IP.equals(ip)) {
                InetAddress iNet = null;
                try {
                    iNet = InetAddress.getLocalHost();  //根据网卡取本机配置的IP
                } catch (UnknownHostException e) {
                    log.error("InetAddress getLocalHost error In HttpUtils getRealIpAddress: ", e);
                }
                ip = iNet.getHostAddress(); //获取本机IP
            }
        }
        return ip;
    }
}
