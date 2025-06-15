package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.AddressDomain;

public interface UpdateAddressUseCase {

    AddressDomain update(String id, AddressDomain addressDomain);
}
