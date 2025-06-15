package com.example.drivewatch.entrypoint.api.facade;

import com.example.drivewatch.core.domain.CompanyDomain;
import com.example.drivewatch.core.usecase.CreateCompanyUseCase;
import com.example.drivewatch.core.usecase.GetAllCompaniesUseCase;
import com.example.drivewatch.core.usecase.UpdateCompanyUseCase;
import com.example.drivewatch.core.usecase.GetCompanyUseCase;
import com.example.drivewatch.entrypoint.api.dto.CompanyDTO;
import com.example.drivewatch.entrypoint.api.dto.CompanyRequestDTO;
import com.example.drivewatch.entrypoint.api.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyFacade {

    private final CompanyMapper companyMapper;

    private final CreateCompanyUseCase createCompanyUseCase;

    private final GetAllCompaniesUseCase getAllCompaniesUseCase;

    private final GetCompanyUseCase getCompanyUseCase;

    private final UpdateCompanyUseCase updateCompanyUseCase;

    public CompanyDTO create(CompanyRequestDTO companyDTO) {
        CompanyDomain companyDomain = companyMapper.toDomain(companyDTO);

        return companyMapper.toDto(createCompanyUseCase.create(companyDomain));
    }

    public List<CompanyDTO> getAll() {
        return companyMapper.toDto(getAllCompaniesUseCase.getAll());
    }

    public CompanyDTO get(String id) {
        return companyMapper.toDto(getCompanyUseCase.get(id));
    }

    public CompanyDTO update(String id, CompanyRequestDTO companyDTO) {
        CompanyDomain companyDomain = companyMapper.toDomain(companyDTO);

        return companyMapper.toDto(updateCompanyUseCase.update(id, companyDomain));
    }
}
