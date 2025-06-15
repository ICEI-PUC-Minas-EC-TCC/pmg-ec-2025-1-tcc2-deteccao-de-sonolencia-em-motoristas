package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.PhoneDomain;
import com.example.drivewatch.core.gateway.PhoneGateway;
import com.example.drivewatch.core.usecase.UpdatePhoneUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePhoneUseCaseImpl implements UpdatePhoneUseCase {

    private final PhoneGateway gateway;

    @Override
    public PhoneDomain update(String id, PhoneDomain phoneDomain) {
        PhoneDomain oldPhone = gateway.get(id);

        PhoneDomain newPhone = PhoneDomain.builder()
            .id(id)
            .idDevice(phoneDomain.idDevice() == null ? oldPhone.idDevice() : phoneDomain.idDevice())
            .areaCode(phoneDomain.areaCode() == null ? oldPhone.areaCode() : phoneDomain.areaCode())
            .phoneNumber(phoneDomain.phoneNumber() == null ? oldPhone.phoneNumber() : phoneDomain.phoneNumber())
            .build();

        return gateway.update(newPhone);
    }
}
