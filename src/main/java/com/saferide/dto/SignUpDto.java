package com.saferide.dto;

import com.saferide.entity.Address;
import com.saferide.entity.Member;
import com.saferide.entity.Role;
import com.saferide.oauth2.KeyGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

//회원가입 시 사용하는 dto
@Data
public class SignUpDto {

    @NotNull(message = "이메일 입력은 필수입니다.")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @NotNull(message = "이름 입력은 필수입니다.")
    private String name;

    @NotNull(message = "패스워드 입력은 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "패스워드는 최소 8자 이상, 대문자와 숫자를 포함해야 합니다.")
    private String password;

    @NotNull(message = "패스워드 확인은 필수입니다.")
    private String confirmPassword;

    private String memberKey;

    private Role role;

    private String roadAddress;

    private String addressDetail;

    @Column(length = 10)
    private String zipcode;

    public static Member toEntity(SignUpDto signUpDto, PasswordEncoder passwordEncoder) {

        // Check if passwords match
        if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            throw new IllegalArgumentException("패스워드와 확인이 일치하지 않습니다.");
        }

        return Member.builder()
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .memberKey(KeyGenerator.generateKey())
                .role(Role.USER)
                .address(new Address(signUpDto.getRoadAddress(), signUpDto.getAddressDetail(), signUpDto.getZipcode()))
                .build();
    }
}
