package com.saferide.repository;

import com.saferide.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 쿼리 메서드
    Optional<Member> findByEmail(String email);

}
