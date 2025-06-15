package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.DeviceDomain;
import com.example.drivewatch.core.gateway.DeviceGateway;
import com.example.drivewatch.core.usecase.GetAllDevicesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class GetAllDevicesUseCaseImpl implements GetAllDevicesUseCase {

    private final DeviceGateway gateway;

    @Override
    public List<DeviceDomain> getAll() {
        return gateway.getAll();
    }
}
