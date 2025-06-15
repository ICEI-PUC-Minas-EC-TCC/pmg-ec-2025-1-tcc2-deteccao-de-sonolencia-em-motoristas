package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.CompanyDomain;
import com.example.drivewatch.core.gateway.CompanyGateway;
import com.example.drivewatch.core.usecase.GetCompanyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCompanyUseCaseImpl implements GetCompanyUseCase {

    private final CompanyGateway gateway;

    @Override
    public CompanyDomain get(String id) {
        return gateway.get(id);
    }
}
