package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.PhoneDomain;
import com.example.drivewatch.core.gateway.PhoneGateway;
import com.example.drivewatch.core.usecase.GetPhoneByDeviceIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPhoneByDeviceIdUseCaseImpl implements GetPhoneByDeviceIdUseCase {

    private final PhoneGateway gateway;

    @Override
    public PhoneDomain getByIdDevice(String id) {

        return gateway.getByDeviceId(id);
    }
}
