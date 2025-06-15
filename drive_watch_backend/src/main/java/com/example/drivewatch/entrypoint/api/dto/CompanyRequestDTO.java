package com.example.drivewatch.entrypoint.api.dto;

import lombok.Builder;

@Builder
public record CompanyRequestDTO(
    String name,
    String email,
    String contract
) {
}
