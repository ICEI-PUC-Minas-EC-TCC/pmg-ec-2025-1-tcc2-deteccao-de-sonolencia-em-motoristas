package com.example.drivewatch.core.gateway;

import com.example.drivewatch.core.domain.CompanyDomain;

import java.util.List;

public interface CompanyGateway {

    CompanyDomain create(CompanyDomain companyDomain);

    List<CompanyDomain> getAll();

    CompanyDomain get(String id);

    CompanyDomain update(CompanyDomain companyDomain);
}
