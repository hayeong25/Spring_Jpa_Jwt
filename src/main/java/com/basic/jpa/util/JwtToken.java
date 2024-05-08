package com.basic.jpa.util;

import com.basic.jpa.dto.TokenDto;
import com.basic.jpa.exception.ClientException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import java.security.Key;
import java.util.Date;

public class JwtToken {
    private static final String ACCESS_TOKEN_SECRET_KEY = "QkFTSUNTRUNSRVRLRVk="; // BASICSECRETKEY
    private static final String JWT = "JWT";

    /*
     * Access Token & Refresh Token 생성
     */
    public static TokenDto publishToken(Authentication authentication) {
        Key key = getHmacKey();

        String authorities = authentication.getAuthorities()
                                           .stream()
                                           .map(GrantedAuthority::getAuthority)
                                           .collect(Collectors.joining(","));

        // Access Token
        String accessToken = Jwts.builder()
                                 .header()
                                 .type(JWT)
                                 .and()
                                 .subject(authentication.getName())
                                 .signWith(key)
                                 .claim("auth", authorities)
                                 .issuedAt(new Date())
                                 .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30))) // 30분
                                 .compact();

        // Refresh Token
        String refreshToken = Jwts.builder()
                                  .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 14))) // 14일
                                  .signWith(key)
                                  .compact();

        return TokenDto.builder()
                       .accessToken(accessToken)
                       .refreshToken(refreshToken)
                       .build();
    }

    /*
     * Token Validation
     */
    public static Authentication validateToken(String accessToken) {
        try {
            Jwts.parser()
                .setSigningKey(getHmacKey())
                .build()
                .parseSignedClaims(accessToken);
        } catch (ExpiredJwtException e) {
            throw new ClientException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        return getAuthentication(accessToken);
    }

    /*
     * get Token Details
     */
    public static Authentication getAuthentication(String accessToken) {
        Claims claims = Jwts.parser()
                            .setSigningKey(getHmacKey())
                            .build()
                            .parseSignedClaims(accessToken)
                            .getPayload();

        if (ObjectUtils.isEmpty(claims.get("auth"))) {
            throw new ClientException(ErrorCode.INVALID_TOKEN);
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                                                                   .map(SimpleGrantedAuthority::new)
                                                                   .collect(Collectors.toList());

        UserDetails user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user, "", authorities);
    }

    private static Key getHmacKey() {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_TOKEN_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}