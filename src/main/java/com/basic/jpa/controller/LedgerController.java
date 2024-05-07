package com.basic.jpa.controller;

import com.basic.jpa.dto.LedgerDto;
import com.basic.jpa.dto.LedgerRequestDto.*;
import com.basic.jpa.service.LedgerService;
import com.basic.jpa.util.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ledger")
public class LedgerController {
    @Autowired
    LedgerService ledgerService;

    /*
     * 가계부 등록
     */
    @PostMapping("/register")
    public ResponseEntity<LedgerDto> registerLedger(@RequestBody @Valid RegisterRequestDto request, HttpServletRequest servletRequest) {
        String memberId = JwtToken.getMemberId(servletRequest);
        return ResponseEntity.ok(ledgerService.registerLedger(request, memberId));
    }

    /*
     * 가계부 수정
     */
    @PostMapping("/modify")
    public ResponseEntity<LedgerDto> modifyLedger(@RequestBody @Valid ModifyRequestDto request, HttpServletRequest servletRequest) {
        String memberId = JwtToken.getMemberId(servletRequest);
        return ResponseEntity.ok(ledgerService.modifyLedger(request, memberId));
    }

    /*
     * 가계부 삭제
     */
    @PostMapping("/remove")
    public ResponseEntity<LedgerDto> removeLedger(@RequestBody @Valid RemoveRequestDto request, HttpServletRequest servletRequest) {
        String memberId = JwtToken.getMemberId(servletRequest);
        return ResponseEntity.ok(ledgerService.removeLedger(request, memberId));
    }
}