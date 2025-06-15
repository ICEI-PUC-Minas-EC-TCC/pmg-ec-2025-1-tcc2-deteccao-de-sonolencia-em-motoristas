package com.example.drivewatch.entrypoint.api.dto;

import lombok.Builder;

@Builder
public record AddressRequestDTO(
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
