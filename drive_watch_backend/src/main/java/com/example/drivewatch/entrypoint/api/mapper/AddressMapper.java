package com.example.drivewatch.entrypoint.api.mapper;

import com.example.drivewatch.core.domain.AddressDomain;
import com.example.drivewatch.entrypoint.api.dto.AddressRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.AddressResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressDomain toDomain(AddressRequestDTO addressRequestDTO);

    AddressResponseDTO toDto(AddressDomain addressDomain);
}
