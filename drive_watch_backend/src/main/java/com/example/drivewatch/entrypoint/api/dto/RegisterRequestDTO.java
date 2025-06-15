package com.example.drivewatch.entrypoint.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RegisterRequestDTO(
    String idDevice,
    String type,
    String image,
    LocalDateTime occurrenceDate
) {
}
