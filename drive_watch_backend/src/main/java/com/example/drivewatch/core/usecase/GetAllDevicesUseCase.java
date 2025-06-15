package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.DeviceDomain;

import java.util.List;

public interface GetAllDevicesUseCase {

    List<DeviceDomain> getAll();
}
