package com.saferide.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginResponse {

    @NotBlank
    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
