package com.example.drivewatch.entrypoint.api.mapper;

import com.example.drivewatch.core.domain.DeviceDomain;
import com.example.drivewatch.entrypoint.api.dto.DeviceRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.DeviceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceMapper {

    DeviceDomain toDomain(DeviceRequestDTO requestDTO);

    DeviceResponseDTO toDto(DeviceDomain deviceDomain);

    List<DeviceResponseDTO> toDto(List<DeviceDomain> deviceDomains);
}
