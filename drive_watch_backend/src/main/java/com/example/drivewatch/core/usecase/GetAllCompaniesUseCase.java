package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.CompanyDomain;

import java.util.List;

public interface GetAllCompaniesUseCase {

    List<CompanyDomain> getAll();
}
