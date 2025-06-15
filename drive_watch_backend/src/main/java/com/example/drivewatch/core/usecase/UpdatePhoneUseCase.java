package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.PhoneDomain;

public interface UpdatePhoneUseCase {

    public PhoneDomain update(String id, PhoneDomain phoneDomain);
}
