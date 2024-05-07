package com.basic.jpa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class LedgerRequestDto {
    @Getter
    @Setter
    public static class RegisterRequestDto {
        @NotBlank(message = "수입/지출/저축 카테고리를 입력하세요.")
        private String category;
        @NotBlank(message = "상세 내역을 입력하세요.")
        private String detail;
        @NotBlank(message = "결제수단을 입력하세요.")
        private String scheme;
        @Min(value = 1, message = "금액을 입력하세요.")
        private long amount;
    }

    @Getter
    @Setter
    public static class ModifyRequestDto {
        @Min(value = 1, message = "수정할 가계부를 선택하세요.")
        private Long id;
        @NotBlank(message = "수입/지출/저축 카테고리를 입력하세요.")
        private String category;
        @NotBlank(message = "상세 내역을 입력하세요.")
        private String detail;
        @NotBlank(message = "결제수단을 입력하세요.")
        private String scheme;
        @Min(value = 1, message = "금액을 입력하세요.")
        private long amount;
    }

    @Getter
    @Setter
    public static class RemoveRequestDto {
        @Min(value = 1, message = "삭제할 가계부를 선택하세요.")
        private Long id;
    }
}