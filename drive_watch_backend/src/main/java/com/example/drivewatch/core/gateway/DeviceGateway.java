package com.example.drivewatch.core.gateway;

import com.example.drivewatch.core.domain.DeviceDomain;

import java.util.List;

public interface DeviceGateway {

    DeviceDomain create(DeviceDomain deviceDomain);

    DeviceDomain get(String id);

    List<DeviceDomain> getAll();

    DeviceDomain update(DeviceDomain deviceDomain);
}
