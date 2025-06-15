package com.example.drivewatch.entrypoint.api.dto;

import lombok.Builder;

@Builder
public record DeviceResponseDTO(
    String id,
    String idCompany,
    String plate,
    String version) {
}
