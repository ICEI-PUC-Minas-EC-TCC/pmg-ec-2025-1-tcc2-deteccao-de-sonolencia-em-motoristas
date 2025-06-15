package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.PhoneDomain;
import com.example.drivewatch.core.gateway.PhoneGateway;
import com.example.drivewatch.core.usecase.CreatePhoneUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePhoneUseCaseImpl implements CreatePhoneUseCase {

    private final PhoneGateway gateway;

    @Override
    public PhoneDomain create(PhoneDomain phoneDomain) {
        return gateway.create(phoneDomain);
    }
}
