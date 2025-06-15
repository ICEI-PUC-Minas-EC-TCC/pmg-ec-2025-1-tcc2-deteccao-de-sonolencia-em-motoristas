package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.RegisterDomain;
import com.example.drivewatch.core.gateway.RegisterGateway;
import com.example.drivewatch.core.usecase.GetRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetRegisterUseCaseImpl implements GetRegisterUseCase {

    private final RegisterGateway gateway;

    @Override
    public RegisterDomain get(String id) {

        return gateway.get(id);
    }
}
