package com.basic.jpa.service;

import com.basic.jpa.dto.MemberDto;
import com.basic.jpa.dto.MemberRequestDto.*;
import com.basic.jpa.entity.Member;
import com.basic.jpa.exception.ClientException;
import com.basic.jpa.repository.MemberRepository;
import com.basic.jpa.util.ErrorCode;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    private static final String MEMBER = "member";

    public MemberDto join(JoinRequestDto request) {
        Optional<Member> preMember = memberRepository.findByMemberId(request.getId());

        if (preMember.isPresent()) {
            throw new ClientException(ErrorCode.DUPLICATE_ID);
        }

        Member newMember = memberRepository.save(Member.builder()
                                                       .memberId(request.getId())
                                                       .password(encodePassword(request.getPassword()))
                                                       .name(request.getName())
                                                       .phone(request.getPhone())
                                                       .email(request.getEmail())
                                                       .useYn(true)
                                                       .role(MEMBER)
                                                       .build());

        return MemberDto.builder()
                        .id(newMember.getMemberId())
                        .name(newMember.getName())
                        .build();
    }

    public MemberDto login(LoginRequestDto request) {
        Member member = getMemberByMemberId(request.getId());

        if (!member.getPassword().equals(encodePassword(request.getPassword()))) {
            throw new ClientException(ErrorCode.INVALID_PASSWORD);
        }

        if (!member.isUseYn()) {
            throw new ClientException(ErrorCode.INVALID_ACCOUNT);
        }

        return MemberDto.builder()
                        .id(member.getMemberId())
                        .name(member.getName())
                        .build();
    }

    public MemberDto getMemberInfo(String memberId) {
        Member member = getMemberByMemberId(memberId);

        return MemberDto.builder()
                        .id(member.getMemberId())
                        .name(member.getName())
                        .phone(member.getPhone())
                        .email(member.getEmail())
                        .build();
    }

    public MemberDto modifyPassword(ModifyPasswordRequestDto request) {
        Member member = getMemberByMemberId(request.getId());

        if (member.getPassword().equals(encodePassword(request.getPrePassword()))) {
            throw new ClientException(ErrorCode.INVALID_PASSWORD);
        }

        member.setPassword(encodePassword(request.getNewPassword()));

        memberRepository.save(member);

        return MemberDto.builder()
                        .id(member.getMemberId())
                        .name(member.getName())
                        .build();
    }

    private String encodePassword(String password) {
        return Base64.encodeBase64String(password.getBytes(StandardCharsets.UTF_8));
    }

    private Member getMemberByMemberId(String memberId) {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);

        if (findMember.isEmpty()) {
            throw new ClientException(ErrorCode.INVALID_ID);
        }

        return findMember.get();
    }
}