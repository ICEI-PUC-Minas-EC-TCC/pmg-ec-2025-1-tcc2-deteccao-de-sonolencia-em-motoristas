package com.example.drivewatch.core.domain;

import lombok.Builder;

@Builder
public record DeviceDomain(
    String id,
    String idCompany,
    String plate,
    String version
) {
}
