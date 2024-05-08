package com.basic.jpa.interceptor;

import com.basic.jpa.dto.AuthDto;
import com.basic.jpa.dto.MemberRequestDto.LoginRequestDto;
import com.basic.jpa.dto.TokenDto;
import com.basic.jpa.util.JwtToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.naming.AuthenticationException;

/*
 * Login validation
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);

        setFilterProcessesUrl("/auth/login");

        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        logger.info("로그인 시드 : JwtAuthenticationFilter.attemptAuthentication");

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getId(), loginRequestDto.getPassword()
                    )
            );
        } catch (Exception e) {
            logger.error("로그인 실패 : {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        logger.info("로그인 성공 : successfulAuthentication");

        try {
            AuthDto auth = (AuthDto) authResult.getPrincipal();

            TokenDto token = JwtToken.publishToken(authResult);

            response.addHeader(HttpHeaders.AUTHORIZATION, token.getAccessToken());
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().println("Login Success");
        } catch (Exception e) {
            logger.error("Login Success But Error : {}", e.getMessage(), e);
        }
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        logger.info("로그인 실패 : unsuccessfulAuthentication");

        try {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println("Login Fail");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Login Fail");
        } catch (Exception e) {
            logger.error("Login Fail Error : {}", e.getMessage(), e);
        }
    }
}