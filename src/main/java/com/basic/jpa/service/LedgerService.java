package com.basic.jpa.service;

import com.basic.jpa.dto.LedgerDto;
import com.basic.jpa.dto.LedgerRequestDto.*;
import com.basic.jpa.entity.Ledger;
import com.basic.jpa.entity.Member;
import com.basic.jpa.exception.ClientException;
import com.basic.jpa.repository.LedgerRepository;
import com.basic.jpa.repository.MemberRepository;
import com.basic.jpa.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LedgerService {

    @Autowired
    LedgerRepository ledgerRepository;

    @Autowired
    MemberRepository memberRepository;

    public LedgerDto registerLedger(RegisterRequestDto request, String memberId) {
        Member member = getMemberByMemberId(memberId);

        Ledger ledger = ledgerRepository.save(Ledger.builder()
                                                    .member(member)
                                                    .category(request.getCategory())
                                                    .detail(request.getDetail())
                                                    .scheme(request.getScheme())
                                                    .amount(request.getAmount())
                                                    .build());

        return LedgerDto.builder()
                        .id(ledger.getId())
                        .category(ledger.getCategory())
                        .detail(ledger.getDetail())
                        .scheme(ledger.getScheme())
                        .amount(ledger.getAmount())
                        .build();
    }

    public LedgerDto modifyLedger(ModifyRequestDto request, String memberId) {
        Member member = getMemberByMemberId(memberId);

        Ledger ledger = getLedgerByLedgerId(request.getId());

        if (member != ledger.getMember()) {
            throw new ClientException(ErrorCode.NOT_OWNER);
        }

        ledger.setCategory(request.getCategory());
        ledger.setDetail(request.getDetail());
        ledger.setScheme(request.getScheme());
        ledger.setAmount(request.getAmount());

        ledgerRepository.save(ledger);

        return LedgerDto.builder()
                        .id(ledger.getId())
                        .category(ledger.getCategory())
                        .detail(ledger.getDetail())
                        .scheme(ledger.getScheme())
                        .amount(ledger.getAmount())
                        .build();
    }

    public LedgerDto removeLedger(RemoveRequestDto request, String memberId) {
        Member member = getMemberByMemberId(memberId);

        Ledger ledger = getLedgerByLedgerId(request.getId());

        if (member != ledger.getMember()) {
            throw new ClientException(ErrorCode.NOT_OWNER);
        }

        ledgerRepository.deleteById(request.getId());

        return LedgerDto.builder()
                        .id(ledger.getId())
                        .build();
    }

    private Member getMemberByMemberId(String memberId) {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);

        if (findMember.isEmpty()) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        return findMember.get();
    }

    private Ledger getLedgerByLedgerId(Long ledgerId) {
        Optional<Ledger> findLedger = ledgerRepository.findById(ledgerId);

        if (findLedger.isEmpty()) {
            throw new ClientException(ErrorCode.INVALID_LEDGER);
        }

        return findLedger.get();
    }
}