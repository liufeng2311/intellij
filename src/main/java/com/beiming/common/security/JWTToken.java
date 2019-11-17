package com.beiming.common.security;

import com.beiming.common.enums.ResultCodeEnums;
import com.beiming.common.exception.BusinessException;
import com.beiming.common.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;

public class JWTToken {

    public static final String SECURITY_KEY = "bmaj1dlx4503ejdiw8jq28liu";


    /**
     * @param id      一般存储用户的唯一ID
     * @param issuer  一般存储用户的姓名
     * @param subject 一般存储用户的全部信息
     * @return
     */
    public static String createJWT(String id, String issuer, String subject) {
        System.out.println(id);
        System.out.println(issuer);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = SECURITY_KEY.getBytes(StandardCharsets.UTF_8);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setId(id) //设置ID
                .setIssuedAt(DateUtils.localDateTime2Date(LocalDateTime.now())) //设置时间
                .setSubject(subject) //设置数据
                .setIssuer(issuer) //设置发布者
                .signWith(signatureAlgorithm, signingKey) //设置秘钥和加密算法
                .setExpiration(DateUtils.localDateTime2Date(LocalDateTime.now().plusMinutes(60))); //一小时过期
        return builder.compact();
    }

    public static Claims parseJWT(String jwt) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            throw new BusinessException(ResultCodeEnums.VAILD_TOKEN, "Authorization解析失败");
        }
        return claims;
    }
}
