package com.basic.jpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class MemberRequestDto {
    @Getter
    @Setter
    public static class LoginRequestDto {
        @NotBlank
        private String id;
        @NotBlank
        private String password;
    }

    @Getter
    @Setter
    public static class JoinRequestDto {
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9]{3,15}]$", message = "영문, 숫자 3~15자여야 합니다.")
        private String id;
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@!%*#?&])[A-Za-z\\d@!%*#?&]{8,}$", message = "영문, 숫자, 특수문자 포함 8~16자여야 합니다.")
        private String password;
        @NotBlank
        private String name;
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식에 맞지 않습니다.")
        private String email;
        @NotBlank
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호를 확인하세요.")
        private String phone;
    }

    @Getter
    @Setter
    public static class ModifyPasswordRequestDto {
        @NotBlank
        private String id;
        @NotBlank
        private String prePassword;
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@!%*#?&])[A-Za-z\\d@!%*#?&]{8,}$", message = "영문, 숫자, 특수문자 포함 8~16자여야 합니다.")
        private String newPassword;
    }

    @Getter
    @Setter
    public static class WithdrawRequestDto {
        @NotBlank
        private String id;
        @NotBlank
        private String password;
    }
}