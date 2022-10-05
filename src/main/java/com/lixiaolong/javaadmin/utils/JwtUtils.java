package com.lixiaolong.javaadmin.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具类
 * 1.生成jwt
 * 2.检验jwt
 */


@Data
@Component
@ConfigurationProperties(prefix = "admin.jwt")
public class JwtUtils {

    private long expire;
    private String secret;
    private String header;

    //生成jwt
    public String generateToken( String username){

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 *expire);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)//几天过期
                .signWith(SignatureAlgorithm.HS512,secret)//算法 密钥
                .compact();
    }
    //解析jwt

    public Claims getClaimsByToken(String jwt){
        try {
            return  Jwts.parser()//解析器
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        }catch (Exception e){
            return null;
        }


    }



    //验证jwt是否过期

    public boolean isTokenExpire(Claims claims){
        return claims.getExpiration().before(new Date());//判断是否在现在时间之前

    }
}
