package com.saferide.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

//로그인시 사용하는 dto
public class LoginDto {

    @Data
    public static class Request {
        @NotNull(message = "이메일 입력은 필수입니다.")
        @Email(message = "유효한 이메일 주소를 입력하세요.")
        private String email;

        @NotNull(message = "패스워드 입력은 필수입니다.")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
                message = "패스워드는 최소 8자 이상, 대문자와 숫자를 포함해야 합니다.")
        private String password;
    }
}
