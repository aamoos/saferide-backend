package com.saferide.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberEditRequest {
    @NotBlank
    private String name;

    private AddressDto address;
}
