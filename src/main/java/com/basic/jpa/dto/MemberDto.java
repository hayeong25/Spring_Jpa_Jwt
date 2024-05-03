package com.basic.jpa.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
    private String id;
    private String name;
    private String phone;
    private String email;
}