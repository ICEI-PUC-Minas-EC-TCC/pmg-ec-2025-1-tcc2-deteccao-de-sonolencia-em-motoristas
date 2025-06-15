package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.DeviceDomain;

public interface CreateDeviceUseCase {

    public DeviceDomain create(DeviceDomain deviceDomain);
}
