package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.RegisterDomain;
import com.example.drivewatch.core.gateway.RegisterGateway;
import com.example.drivewatch.core.usecase.CreateRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRegisterUseCaseImpl implements CreateRegisterUseCase {

    private final RegisterGateway gateway;

    @Override
    public RegisterDomain create(RegisterDomain registerDomain) {
        return gateway.create(registerDomain);
    }
}
