package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.PhoneDomain;

public interface GetPhoneByDeviceIdUseCase {

    public PhoneDomain getByIdDevice(String id);
}
