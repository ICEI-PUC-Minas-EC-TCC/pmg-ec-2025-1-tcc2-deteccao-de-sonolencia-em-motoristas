package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.AddressDomain;

public interface GetAddressByCompanyIdUseCase {

    public AddressDomain getByCompanyId(String id);
}
