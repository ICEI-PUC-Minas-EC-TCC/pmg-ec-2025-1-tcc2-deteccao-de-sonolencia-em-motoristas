package com.example.drivewatch.core.gateway;

import com.example.drivewatch.core.domain.RegisterDomain;

import java.util.List;

public interface RegisterGateway {

    RegisterDomain create(RegisterDomain registerDomain);

    RegisterDomain get(String id);

    List<RegisterDomain> getAll();

    List<RegisterDomain> getAllByIdDevice(String id);

    RegisterDomain update(RegisterDomain registerDomain);
}
