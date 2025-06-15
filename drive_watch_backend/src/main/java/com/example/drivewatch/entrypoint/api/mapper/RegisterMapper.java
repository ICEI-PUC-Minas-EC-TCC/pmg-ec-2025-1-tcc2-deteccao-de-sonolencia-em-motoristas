package com.example.drivewatch.entrypoint.api.mapper;

import com.example.drivewatch.core.domain.RegisterDomain;
import com.example.drivewatch.entrypoint.api.dto.RegisterRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.RegisterResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegisterMapper {

    RegisterDomain toDomain(RegisterRequestDTO requestDTO);

    RegisterResponseDTO toDto(RegisterDomain registerDomain);

    List<RegisterResponseDTO> toDto(List<RegisterDomain> registerDomains);
}
