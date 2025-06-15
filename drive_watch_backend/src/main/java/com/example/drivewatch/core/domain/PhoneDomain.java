package com.example.drivewatch.core.domain;

import lombok.Builder;

@Builder
public record PhoneDomain(
    String id,
    String idDevice,
    String areaCode,
    String phoneNumber
) {
}
