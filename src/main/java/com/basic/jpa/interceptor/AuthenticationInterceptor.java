package com.basic.jpa.interceptor;

import com.basic.jpa.exception.ClientException;
import com.basic.jpa.util.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    String[] exceptUri = {"/auth/token", "/member/join", "/member/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("==================== START ====================");

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (!Arrays.asList(exceptUri).contains(request.getRequestURI())) {
            try {
                String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).replaceFirst("Bearer ", "");
            } catch (Exception e) {
                throw new ClientException(ErrorCode.INVALID_TOKEN);
            }
        }

//        Jws<Claims> jws = Jwts.parser().build().parseSignedClaims(token);

        return true;
    }
}