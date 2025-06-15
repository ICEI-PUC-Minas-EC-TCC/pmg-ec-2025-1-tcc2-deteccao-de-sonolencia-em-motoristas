package com.example.drivewatch.core.domain;

import lombok.Builder;

@Builder
public record AddressDomain(
    String id,
    String idCompany,
    String zipCode,
    String street,
    Integer number,
    String neighborhood,
    String city,
    String state,
    String country
) {
}
