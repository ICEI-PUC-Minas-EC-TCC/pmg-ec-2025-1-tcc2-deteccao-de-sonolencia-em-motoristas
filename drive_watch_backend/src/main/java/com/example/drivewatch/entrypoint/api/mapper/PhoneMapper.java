package com.example.drivewatch.entrypoint.api.mapper;

import com.example.drivewatch.core.domain.PhoneDomain;
import com.example.drivewatch.entrypoint.api.dto.PhoneRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.PhoneResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhoneMapper {

    PhoneDomain toDomain(PhoneRequestDTO requestDTO);

    PhoneResponseDTO toDto(PhoneDomain phoneDomain);
}
