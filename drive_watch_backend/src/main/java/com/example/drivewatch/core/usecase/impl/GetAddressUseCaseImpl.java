package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.AddressDomain;
import com.example.drivewatch.core.gateway.AddressGateway;
import com.example.drivewatch.core.usecase.GetAddressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAddressUseCaseImpl implements GetAddressUseCase {

    private final AddressGateway gateway;

    @Override
    public AddressDomain get(String id) {
        return gateway.get(id);
    }
}
