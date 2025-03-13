package com.saferide.service;

import com.saferide.config.CustomUserInfoDto;
import com.saferide.dto.LoginDto;
import com.saferide.dto.MemberDto;
import com.saferide.entity.Member;
import com.saferide.error.CustomException;
import com.saferide.error.ErrorCode;
import com.saferide.jwt.JwtUtil;
import com.saferide.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

import static com.saferide.error.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public String login(LoginDto.Request request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if(!encoder.matches(password, member.getPassword())) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        CustomUserInfoDto info = Member.customUserToDto(member);
        return jwtUtil.createAccessToken(info);
    }
}
