package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.AddressDomain;
import com.example.drivewatch.core.gateway.AddressGateway;
import com.example.drivewatch.core.usecase.CreateAddressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAddressUseCaseImpl implements CreateAddressUseCase {

    private final AddressGateway gateway;

    @Override
    public AddressDomain create(AddressDomain addressDomain) {

        return gateway.create(addressDomain);
    }
}
