package com.example.drivewatch.entrypoint.api.dto;

import lombok.Builder;

@Builder
public record AddressResponseDTO(
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
