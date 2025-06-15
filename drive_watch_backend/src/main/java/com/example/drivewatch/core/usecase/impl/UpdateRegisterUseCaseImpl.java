package com.example.drivewatch.core.usecase.impl;

import com.example.drivewatch.core.domain.RegisterDomain;
import com.example.drivewatch.core.gateway.RegisterGateway;
import com.example.drivewatch.core.usecase.UpdateRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateRegisterUseCaseImpl implements UpdateRegisterUseCase {

    private final RegisterGateway gateway;

    @Override
    public RegisterDomain update(String id, RegisterDomain registerDomain) {
        RegisterDomain oldRegister = gateway.get(id);

        RegisterDomain newRegister = RegisterDomain.builder()
            .id(id)
            .idDevice(registerDomain.idDevice() == null ? oldRegister.idDevice() : registerDomain.idDevice())
            .type(registerDomain.type() == null ? oldRegister.type() : registerDomain.type())
            .image(registerDomain.image() == null ? oldRegister.image() : registerDomain.image())
            .occurrenceDate(registerDomain.occurrenceDate() == null ? oldRegister.occurrenceDate() : registerDomain.occurrenceDate())
            .build();

        return gateway.update(newRegister);
    }
}
