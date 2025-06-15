package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.CompanyDomain;
import com.example.drivewatch.core.gateway.CompanyGateway;
import com.example.drivewatch.core.usecase.UpdateCompanyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCompanyUseCaseImpl implements UpdateCompanyUseCase {

    private final CompanyGateway companyGateway;

    @Override
    public CompanyDomain update(String id, CompanyDomain companyDomain) {
        CompanyDomain oldCompany = companyGateway.get(id);

        CompanyDomain newCompany = CompanyDomain.builder()
            .id(id)
            .name(companyDomain.name() == null ? oldCompany.name() : companyDomain.name())
            .email(companyDomain.email() == null ? oldCompany.email() : companyDomain.email())
            .contract(companyDomain.contract() == null ? oldCompany.contract() : companyDomain.contract())
            .build();

        return companyGateway.update(newCompany);
    }
}
