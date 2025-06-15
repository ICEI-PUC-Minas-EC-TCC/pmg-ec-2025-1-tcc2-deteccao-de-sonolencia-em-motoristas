package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.DeviceDomain;
import com.example.drivewatch.core.gateway.DeviceGateway;
import com.example.drivewatch.core.usecase.GetDeviceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetDeviceUseCaseImpl implements GetDeviceUseCase {

    private final DeviceGateway gateway;

    @Override
    public DeviceDomain get(String id) {
        return gateway.get(id);
    }
}
