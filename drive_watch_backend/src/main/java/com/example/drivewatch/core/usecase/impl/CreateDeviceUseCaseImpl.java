package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.DeviceDomain;
import com.example.drivewatch.core.gateway.DeviceGateway;
import com.example.drivewatch.core.usecase.CreateDeviceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateDeviceUseCaseImpl implements CreateDeviceUseCase {

    private final DeviceGateway gateway;

    @Override
    public DeviceDomain create(DeviceDomain deviceDomain) {
        return gateway.create(deviceDomain);
    }
}
