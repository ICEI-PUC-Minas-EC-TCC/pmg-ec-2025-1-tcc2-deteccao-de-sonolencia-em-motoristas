package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.DeviceDomain;
import com.example.drivewatch.core.gateway.DeviceGateway;
import com.example.drivewatch.core.usecase.UpdateDeviceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateDeviceUseCaseImpl implements UpdateDeviceUseCase {

    private final DeviceGateway gateway;

    @Override
    public DeviceDomain update(String id, DeviceDomain deviceDomain) {
        DeviceDomain oldDevice = gateway.get(id);

        DeviceDomain newDevice = DeviceDomain.builder()
            .id(id)
            .idCompany(deviceDomain.idCompany() == null ? oldDevice.idCompany() : deviceDomain.idCompany())
            .plate(deviceDomain.plate() == null ? oldDevice.plate() : deviceDomain.plate())
            .version(deviceDomain.version() == null ? oldDevice.version() : deviceDomain.version())
            .build();

        return gateway.update(newDevice);
    }
}
