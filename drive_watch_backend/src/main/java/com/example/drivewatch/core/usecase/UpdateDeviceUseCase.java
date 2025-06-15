package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.DeviceDomain;

public interface UpdateDeviceUseCase {

    public DeviceDomain update(String id, DeviceDomain deviceDomain);
}
