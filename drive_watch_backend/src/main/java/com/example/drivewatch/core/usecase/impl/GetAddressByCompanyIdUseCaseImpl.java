package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.AddressDomain;
import com.example.drivewatch.core.gateway.AddressGateway;
import com.example.drivewatch.core.usecase.GetAddressByCompanyIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAddressByCompanyIdUseCaseImpl implements GetAddressByCompanyIdUseCase {

    private final AddressGateway gateway;

    @Override
    public AddressDomain getByCompanyId(String id) {
        return gateway.getByCompanyId(id);
    }
}
