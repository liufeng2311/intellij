package com.liufeng.common.security;

import com.alibaba.fastjson.JSONObject;
import com.liufeng.common.enums.ResultCodeEnums;
import com.liufeng.common.exception.BusinessException;
import com.liufeng.common.utils.DateUtils;
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
                .setExpiration(DateUtils.localDateTime2Date(LocalDateTime.now().plusMinutes(60))); //半小时过期
        return builder.compact();
    }

    //问题：  获取到的claims.getSubject()与存入的不一致，会带有类信息
    public static JSONObject parseJWT(String jwt) {
        JSONObject object = new JSONObject();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt)
                    .getBody();
            object.put("phone", claims.getIssuer());
            object.put("info", claims.getSubject());
        } catch (Exception e) {
            throw new BusinessException(ResultCodeEnums.VAILD_TOKEN,"Authorization解析失败");
        }
        return object;
    }
}
