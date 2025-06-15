package com.example.drivewatch.core.gateway;

import com.example.drivewatch.core.domain.PhoneDomain;

public interface PhoneGateway {

    PhoneDomain create(PhoneDomain phoneDomain);

    PhoneDomain get(String id);

    PhoneDomain getByDeviceId(String id);

    PhoneDomain update(PhoneDomain phoneDomain);
}
