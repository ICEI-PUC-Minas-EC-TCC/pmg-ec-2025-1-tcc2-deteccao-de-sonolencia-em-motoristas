package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.AddressDomain;

public interface GetAddressUseCase {

    public AddressDomain get(String id);
}
