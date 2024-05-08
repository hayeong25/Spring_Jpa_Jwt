package com.basic.jpa.controller;

import com.basic.jpa.dto.TokenDto;
import com.basic.jpa.util.JwtToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/token")
    public ResponseEntity<TokenDto> publishToken(Authentication authentication) {
        return ResponseEntity.ok(JwtToken.publishToken(authentication));
    }
}