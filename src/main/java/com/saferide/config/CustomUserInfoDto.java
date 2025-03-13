package com.saferide.config;

import com.saferide.entity.RoleType;
import lombok.*;

//내부에서 쓰는 dto
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomUserInfoDto{
    private Long id;
    private String email;
    private String name;
    private String password;
    private RoleType role;

}