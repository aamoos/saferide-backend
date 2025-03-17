package com.saferide.entity;

import com.saferide.audit.BaseTimeEntity;
import com.saferide.dto.MemberEditRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private String password;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String memberKey;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    private String profile;

    @Builder
    public Member(String name, String password, String email, String memberKey, Role role, Address address, String profile) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.memberKey = memberKey;
        this.role = role;
        this.address = address;
        this.profile = profile;
    }

    public void updateMember(MemberEditRequest request) {
        this.name = request.getName();

        if (request.getAddress() != null) {
            this.address = request.getAddress().toEntity();
        }
    }

}
