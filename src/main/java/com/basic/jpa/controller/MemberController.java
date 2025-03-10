package com.basic.jpa.controller;

import com.basic.jpa.dto.AuthDto;
import com.basic.jpa.dto.MemberDto;
import com.basic.jpa.dto.MemberRequestDto.*;
import com.basic.jpa.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<MemberDto> getMemberInfo(@AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(memberService.getMemberInfo(auth.getMember().getMemberId()));
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
}