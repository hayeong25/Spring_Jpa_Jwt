package com.basic.jpa.controller;

import com.basic.jpa.dto.AuthDto;
import com.basic.jpa.dto.LedgerDto;
import com.basic.jpa.dto.LedgerRequestDto.*;
import com.basic.jpa.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledger")
public class LedgerController {
    @Autowired
    LedgerService ledgerService;

    /*
     * 가계부 등록
     */
    @PostMapping("/register")
    public ResponseEntity<LedgerDto> registerLedger(@RequestBody @Valid RegisterRequestDto request, @AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(ledgerService.registerLedger(request, auth.getMember().getMemberId()));
    }

    /*
     * 가계부 수정
     */
    @PutMapping("/modify")
    public ResponseEntity<LedgerDto> modifyLedger(@RequestBody @Valid ModifyRequestDto request, @AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(ledgerService.modifyLedger(request, auth.getMember().getMemberId()));
    }

    /*
     * 가계부 삭제
     */
    @DeleteMapping("/remove")
    public ResponseEntity<LedgerDto> removeLedger(@RequestBody @Valid RemoveRequestDto request, @AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(ledgerService.removeLedger(request, auth.getMember().getMemberId()));
    }
}