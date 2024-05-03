package com.basic.jpa.controller;

import com.basic.jpa.dto.MemberDto;
import com.basic.jpa.dto.MemberRequestDto.*;
import com.basic.jpa.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    /*
     * 회원 등록
     */
    @PostMapping("/register")
    public ResponseEntity<MemberDto> registerMember(@RequestBody @Valid JoinRequestDto request) {
        return ResponseEntity.ok(memberService.join(request));
    }

    /*
     * 회원 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.ok(memberService.login(request));
    }

    /*
     * 회원 정보 가져오기
     */
    @GetMapping("/detail")
    public ResponseEntity<MemberDto> getMemberInfo(HttpServletRequest servletRequest) {
        String memberId = getMemberId(servletRequest);
        return ResponseEntity.ok(memberService.getMemberInfo(memberId));
    }

    /*
     * 회원 비밀번호 수정
     */
    @PutMapping("/modify-password")
    public ResponseEntity<MemberDto> modifyPassword(@RequestBody @Valid ModifyPasswordRequestDto request) {
        return ResponseEntity.ok(memberService.modifyPassword(request));
    }

    /*
     * 회원 탈퇴
     */
    @PutMapping("/withdraw")
    public ResponseEntity<MemberDto> withdraw(@RequestBody @Valid WithdrawRequestDto request) {
        return ResponseEntity.ok(memberService.withdraw(request));
    }

    /*
     * JWT access token으로 ID 가져오기
     */
    private static String getMemberId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replaceFirst("Bearer", "");
        Jws<Claims> jws = Jwts.parser().build().parseSignedClaims(token);
        return jws.getPayload().get("id").toString();
    }
}