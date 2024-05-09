package com.basic.jpa.interceptor;

import com.basic.jpa.dto.TokenDto;
import com.basic.jpa.util.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        TokenDto tokenDto = JwtToken.publishToken(authentication);

        response.setHeader("accessToken", tokenDto.getAccessToken());
        response.setHeader("refreshToken", tokenDto.getRefreshToken());

        response.sendRedirect("/");
    }
}