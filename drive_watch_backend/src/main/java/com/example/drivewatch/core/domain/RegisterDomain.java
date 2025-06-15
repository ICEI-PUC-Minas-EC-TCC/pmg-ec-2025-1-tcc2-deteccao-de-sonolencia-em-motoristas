package com.example.drivewatch.core.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RegisterDomain(
    String id,
    String idDevice,
    String type,
    String image,
    LocalDateTime occurrenceDate
) {
}
