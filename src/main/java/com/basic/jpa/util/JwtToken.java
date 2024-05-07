package com.basic.jpa.util;

import com.basic.jpa.dto.MemberDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

public class JwtToken {
    private static final String ACCESS_TOKEN_SECRET_KEY = "QkFTSUNTRUNSRVRLRVk="; // BASICSECRETKEY
    private static final String JWT = "JWT";

    public static String createAccessToken(MemberDto memberDto) {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                   .header()
                   .type(JWT)
                   .and()
                   .signWith(key)
                   .claim("id", memberDto.getId())
                   .issuedAt(new Date())
                   .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30))) // 30분
                   .compact();
    }

    public static MemberDto verifyAccessToken(String accessToken) {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        Jws<Claims> jws = Jwts.parser()
                              .verifyWith(key)
                              .build()
                              .parseSignedClaims(accessToken);

        LocalDateTime expiredTime = (LocalDateTime) jws.getPayload().get("exp");

        if (LocalDateTime.now().isAfter(expiredTime)) {
            throw new RuntimeException("EXPIRED TOKEN");
        }

        return MemberDto.builder()
                        .id(jws.getPayload().get("id").toString())
                        .build();
    }

    public static String getMemberId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replaceFirst("Bearer", "");
        Jws<Claims> jws = Jwts.parser().build().parseSignedClaims(token);
        return jws.getPayload().get("id").toString();
    }
}