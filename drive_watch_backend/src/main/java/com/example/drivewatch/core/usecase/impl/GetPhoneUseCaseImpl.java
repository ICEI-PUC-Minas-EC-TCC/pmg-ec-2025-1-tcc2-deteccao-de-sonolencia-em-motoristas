package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.PhoneDomain;
import com.example.drivewatch.core.gateway.PhoneGateway;
import com.example.drivewatch.core.usecase.GetPhoneUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPhoneUseCaseImpl implements GetPhoneUseCase {

    private final PhoneGateway gateway;

    @Override
    public PhoneDomain get(String id) {

        return gateway.get(id);
    }
}
