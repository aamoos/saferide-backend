package com.saferide.controller;

import com.saferide.dto.LoginDto;
import com.saferide.dto.LoginResponse;
import com.saferide.dto.SignUpDto;
import com.saferide.jwt.CookieUtil;
import com.saferide.jwt.TokenProvider;
import com.saferide.jwt.TokenService;
import com.saferide.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;

    /**
     * 로그인
     * @param loginDto
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        // Store the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the JWT tokens
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken);

        // Save refresh token in the response cookie
        CookieUtil.setRefreshTokenCookie(response, refreshToken);

        // Return the access token in the response
        return ResponseEntity.ok(new LoginResponse(accessToken));
    }

    /**
     * 로그아웃
     * @param refreshToken
     * @param response
     * @return
     */
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        tokenService.deleteRefreshToken(tokenService.getMemberKey(refreshToken));
        CookieUtil.removeRefreshTokenCookie(response);
        return ResponseEntity.noContent().build();
    }

    /**
     * refresh token으로 access
     * @param refreshToken
     * @param response
     * @return
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is required");
        }

        // Refresh Token 검증 후 새로운 Access Token 발급
        String newAccessToken = tokenProvider.reissueRefreshToken(refreshToken, response);

        if (newAccessToken != null) {
            return ResponseEntity.ok(new LoginResponse(newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
    }

    /**
     * 회원가입
     * @param signUpDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpDto) {
        memberService.registerMember(signUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }
}
