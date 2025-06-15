package com.example.drivewatch.entrypoint.api.mapper;

import com.example.drivewatch.core.domain.CompanyDomain;
import com.example.drivewatch.entrypoint.api.dto.CompanyDTO;
import com.example.drivewatch.entrypoint.api.dto.CompanyRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    CompanyDomain toDomain(CompanyRequestDTO companyDTO);

    CompanyDTO toDto(CompanyDomain companyDomain);

    List<CompanyDTO> toDto(final List<CompanyDomain> companyDomains);
}
