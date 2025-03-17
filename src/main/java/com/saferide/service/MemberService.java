package com.saferide.service;

import com.saferide.dto.LoginDto;
import com.saferide.dto.LoginResponse;
import com.saferide.dto.SignUpDto;
import com.saferide.entity.Member;
import com.saferide.error.CustomException;
import com.saferide.jwt.CookieUtil;
import com.saferide.jwt.TokenProvider;
import com.saferide.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.saferide.error.ErrorCode.INVALID_PASSWORD;
import static com.saferide.error.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    //로그인
    @Transactional(readOnly = true)
    public String login(LoginDto loginDto, HttpServletResponse response) {
        // Retrieve user from the database
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // Verify the password
        if (!encoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new CustomException(INVALID_PASSWORD);
        }

        // Authenticate the user if credentials are valid
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the JWT tokens
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken);

        // Save refresh token in the response cookie
        CookieUtil.setRefreshTokenCookie(response, refreshToken);

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
