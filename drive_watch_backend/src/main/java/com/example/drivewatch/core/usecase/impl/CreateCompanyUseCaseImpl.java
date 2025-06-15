package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.CompanyDomain;
import com.example.drivewatch.core.gateway.CompanyGateway;
import com.example.drivewatch.core.usecase.CreateCompanyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCompanyUseCaseImpl implements CreateCompanyUseCase {

    private final CompanyGateway gateway;

    @Override
    public CompanyDomain create(CompanyDomain companyDomain) {
        return gateway.create(companyDomain);
    }
}
