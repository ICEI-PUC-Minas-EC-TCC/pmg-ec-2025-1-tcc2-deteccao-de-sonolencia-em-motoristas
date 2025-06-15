package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.AddressDomain;
import com.example.drivewatch.core.gateway.AddressGateway;
import com.example.drivewatch.core.usecase.UpdateAddressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAddressUseCaseImpl implements UpdateAddressUseCase {

    private final AddressGateway gateway;

    @Override
    public AddressDomain update(String id, AddressDomain addressDomain) {
        AddressDomain oldAddress = gateway.get(id);

        AddressDomain newAddress = AddressDomain.builder()
            .id(id)
            .idCompany(addressDomain.idCompany() == null ? oldAddress.idCompany() : addressDomain.idCompany())
            .zipCode(addressDomain.zipCode() == null ? oldAddress.zipCode() : addressDomain.zipCode())
            .street(addressDomain.street() == null ? oldAddress.street() : addressDomain.street())
            .number(addressDomain.number() == null ? oldAddress.number() : addressDomain.number())
            .neighborhood(addressDomain.neighborhood() == null ? oldAddress.neighborhood() : addressDomain.neighborhood())
            .city(addressDomain.city() == null ? oldAddress.city() : addressDomain.city())
            .state(addressDomain.state() == null ? oldAddress.state() : addressDomain.state())
            .country(addressDomain.country() == null ? oldAddress.country() : addressDomain.country())
            .build();

        return gateway.update(newAddress);
    }
}
