package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.RegisterDomain;

public interface CreateRegisterUseCase {

    RegisterDomain create(RegisterDomain registerDomain);
}
