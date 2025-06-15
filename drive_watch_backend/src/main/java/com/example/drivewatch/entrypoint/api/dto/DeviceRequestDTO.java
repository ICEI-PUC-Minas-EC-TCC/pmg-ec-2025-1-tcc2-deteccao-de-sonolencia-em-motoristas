package com.example.drivewatch.entrypoint.api.dto;

import lombok.Builder;

@Builder
public record DeviceRequestDTO(
    String idCompany,
    String plate,
    String version
) {
}
