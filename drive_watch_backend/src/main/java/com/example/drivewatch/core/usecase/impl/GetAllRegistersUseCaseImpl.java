package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.RegisterDomain;
import com.example.drivewatch.core.gateway.RegisterGateway;
import com.example.drivewatch.core.usecase.GetAllRegistersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetAllRegistersUseCaseImpl implements GetAllRegistersUseCase {

    private final RegisterGateway gateway;

    @Override
    public List<RegisterDomain> getAll() {
        return gateway.getAll();
    }
}
