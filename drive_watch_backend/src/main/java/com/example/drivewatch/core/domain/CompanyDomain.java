package com.example.drivewatch.core.domain;

import lombok.Builder;

@Builder
public record CompanyDomain(
    String id,
    String name,
    String email,
    String contract
) {
}
