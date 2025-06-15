package com.example.drivewatch.entrypoint.api.dto;

import lombok.Builder;

@Builder
public record PhoneResponseDTO(
    String id,
    String idDevice,
    String areaCode,
    String phoneNumber
) {
}
