package com.example.drivewatch.entrypoint.api.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record CompanyDTO(
    Integer id,
    String name,
    String email,
    String contract
) {
}
