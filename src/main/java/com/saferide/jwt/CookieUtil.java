package com.saferide.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    private static final int COOKIE_EXPIRATION = 60 * 60 * 24 * 30; // 30일

    public static void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // 🚨 로컬에서는 false (배포 시 true)
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(COOKIE_EXPIRATION);
        refreshTokenCookie.setAttribute("SameSite", "Lax");

        response.addCookie(refreshTokenCookie);
    }

    public static void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0);  // Expire the cookie immediately
        refreshTokenCookie.setPath("/");  // Ensure it's the same path
        response.addCookie(refreshTokenCookie);
    }
}
