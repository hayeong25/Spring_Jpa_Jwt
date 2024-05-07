package com.basic.jpa.controller;

import com.basic.jpa.dto.MemberDto;
import com.basic.jpa.util.JwtToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/token")
    public ResponseEntity<String> getAccessToken(MemberDto member) {
        return ResponseEntity.ok(JwtToken.createAccessToken(member));
    }
}