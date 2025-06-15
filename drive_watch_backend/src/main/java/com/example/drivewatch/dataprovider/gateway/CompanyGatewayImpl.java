package com.example.drivewatch.dataprovider.gateway;

import com.example.drivewatch.core.domain.CompanyDomain;
import com.example.drivewatch.core.gateway.CompanyGateway;
import com.example.drivewatch.dataprovider.database.entity.Company;
import com.example.drivewatch.dataprovider.database.mapper.EntityMapper;
import com.example.drivewatch.dataprovider.database.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyGatewayImpl implements CompanyGateway {

    private final CompanyRepository repository;

    private final EntityMapper mapper;

    @Override
    public CompanyDomain create(CompanyDomain companyDomain) {
        Company entity = mapper.toEntity(companyDomain);

        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<CompanyDomain> getAll() {
        return mapper.toDomain((List<Company>) repository.findAll());
    }

    @Override
    public CompanyDomain get(String id) {
        return mapper.toDomain(repository.findById(id).orElse(null));
    }

    @Override
    public CompanyDomain update(CompanyDomain companyDomain) {
        Company entity = mapper.toEntity(companyDomain);

        return mapper.toDomain(repository.save(entity));
    }
}
