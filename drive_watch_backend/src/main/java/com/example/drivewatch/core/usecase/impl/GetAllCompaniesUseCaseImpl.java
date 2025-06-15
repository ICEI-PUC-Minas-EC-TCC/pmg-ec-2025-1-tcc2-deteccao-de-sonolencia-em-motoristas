package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.CompanyDomain;
import com.example.drivewatch.core.gateway.CompanyGateway;
import com.example.drivewatch.core.usecase.GetAllCompaniesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllCompaniesUseCaseImpl implements GetAllCompaniesUseCase {

    private final CompanyGateway gateway;

    @Override
    public List<CompanyDomain> getAll() {
        return gateway.getAll();
    }
}
