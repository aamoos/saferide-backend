package com.saferide.dto;

import com.saferide.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddressDto {
    @NotBlank
    private String roadAddress;

    @NotBlank
    private String addressDetail;

    @NotBlank
    private String zipcode;

    public static AddressDto fromEntity(Address address) {
        return AddressDto.builder()
                .roadAddress(address.getRoadAddress())
                .addressDetail(address.getAddressDetail())
                .zipcode(address.getZipcode())
                .build();
    }

    public Address toEntity() {
        return Address.builder()
                .roadAddress(roadAddress)
                .addressDetail(addressDetail)
                .zipcode(zipcode)
                .build();
    }
}
