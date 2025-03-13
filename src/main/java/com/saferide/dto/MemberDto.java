package com.saferide.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saferide.entity.Member;
import com.saferide.entity.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

public class MemberDto {

    @Data
    public static class Request {

        @NotNull(message = "이메일 입력은 필수입니다.")
        @Email(message = "유효한 이메일 주소를 입력하세요.")
        private String email;

        @NotNull(message = "패스워드 입력은 필수입니다.")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
                message = "패스워드는 최소 8자 이상, 대문자와 숫자를 포함해야 합니다.")
        private String password;

        @NotNull(message = "이름 입력은 필수입니다.")
        private String name;
        private RoleType role;

        public static Member toEntity(MemberDto.Request request){
            return Member.builder()
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .name(request.getName())
                    .role(RoleType.USER)
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Response {
        private String email;
        private String name;
    }
}
