package com.basic.jpa.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LedgerDto {
    private Long id;
    private String category;
    private String detail;
    private String scheme;
    private long amount;
}