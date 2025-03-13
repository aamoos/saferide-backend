package com.saferide.service;

import com.saferide.dto.LoginDto;
import com.saferide.dto.SignUpDto;
import com.saferide.entity.Member;
import com.saferide.error.CustomException;
import com.saferide.jwt.TokenProvider;
import com.saferide.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.saferide.error.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public String login(LoginDto request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // 암호화된 password 검증
        if (!encoder.matches(password, member.getPassword())) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        // Spring Security 인증 객체 생성
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Access Token 및 Refresh Token 생성
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken);

        return accessToken;
    }

    //회원등록
    public void registerMember(SignUpDto request) {

        // Check if the email already exists
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        memberRepository.save(SignUpDto.toEntity(request, encoder));
    }
}
