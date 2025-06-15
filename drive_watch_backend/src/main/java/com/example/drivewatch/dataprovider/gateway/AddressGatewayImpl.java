package com.example.drivewatch.dataprovider.gateway;

import com.example.drivewatch.core.domain.AddressDomain;
import com.example.drivewatch.core.gateway.AddressGateway;
import com.example.drivewatch.dataprovider.database.entity.Address;
import com.example.drivewatch.dataprovider.database.mapper.EntityMapper;
import com.example.drivewatch.dataprovider.database.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressGatewayImpl implements AddressGateway {

    private final AddressRepository repository;

    private final EntityMapper mapper;

    @Override
    public AddressDomain create(AddressDomain addressDomain) {
        Address entity = mapper.toEntity(addressDomain);

        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public AddressDomain getByCompanyId(String id) {
        return mapper.toDomain(repository.findByIdCompany(Integer.parseInt(id)));
    }

    @Override
    public AddressDomain get(String id) {
        return mapper.toDomain(repository.findById(id).orElse(null));
    }

    @Override
    public AddressDomain update(AddressDomain addressDomain) {
        Address address = mapper.toEntity(addressDomain);

        return mapper.toDomain(repository.save(address));
    }
}
