package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.RegisterDomain;
import com.example.drivewatch.core.gateway.RegisterGateway;
import com.example.drivewatch.core.usecase.GetAllRegistersByDeviceIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllRegistersByDeviceIdUseCaseImpl implements GetAllRegistersByDeviceIdUseCase {

    private final RegisterGateway gateway;

    @Override
    public List<RegisterDomain> getAllByDeviceId(String id) {
        return gateway.getAllByIdDevice(id);
    }
}
