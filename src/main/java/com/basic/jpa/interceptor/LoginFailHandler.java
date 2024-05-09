package com.basic.jpa.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@AllArgsConstructor
public class LoginFailHandler implements AuthenticationFailureHandler {
    public LoginFailHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        this.onAuthenticationFailure(request, response, exception);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception instanceof AuthenticationServiceException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SYSTEM ERROR");
        } else if (exception instanceof BadCredentialsException) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "INVALID ID or PASSWORD");
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "INVALID ACCOUNT");
        }

        response.sendRedirect("/auth/login");
    }
}