package com.saferide.entity;

import com.saferide.audit.BaseTimeEntity;
import com.saferide.config.CustomUserInfoDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private RoleType role;

    public static CustomUserInfoDto customUserToDto(Member member){
        return CustomUserInfoDto.builder()
                .id(member.id)
                .email(member.email)
                .name(member.name)
                .password(member.password)
                .role(member.role)
                .build();
    }
}