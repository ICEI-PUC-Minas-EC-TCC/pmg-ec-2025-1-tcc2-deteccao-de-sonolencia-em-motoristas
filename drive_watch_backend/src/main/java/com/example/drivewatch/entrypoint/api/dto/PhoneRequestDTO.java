package com.example.drivewatch.entrypoint.api.dto;

import lombok.Builder;

@Builder
public record PhoneRequestDTO(
    String idDevice,
    String areaCode,
    String phoneNumber
) {
}
