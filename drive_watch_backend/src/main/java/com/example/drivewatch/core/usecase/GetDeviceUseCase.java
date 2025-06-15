package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.DeviceDomain;

public interface GetDeviceUseCase {

    DeviceDomain get(String id);
}
