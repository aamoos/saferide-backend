package com.saferide.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 권한
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String key;
}
