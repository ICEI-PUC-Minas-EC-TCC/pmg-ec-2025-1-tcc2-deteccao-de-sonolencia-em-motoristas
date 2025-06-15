package com.example.drivewatch.dataprovider.gateway;

import com.example.drivewatch.core.domain.DeviceDomain;
import com.example.drivewatch.core.gateway.DeviceGateway;
import com.example.drivewatch.dataprovider.database.entity.Device;
import com.example.drivewatch.dataprovider.database.mapper.EntityMapper;
import com.example.drivewatch.dataprovider.database.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceGatewayImpl implements DeviceGateway {

    private final DeviceRepository repository;

    private final EntityMapper mapper;

    @Override
    public DeviceDomain create(DeviceDomain deviceDomain) {
        Device device = mapper.toEntity(deviceDomain);

        return mapper.toDomain(repository.save(device));
    }

    @Override
    public DeviceDomain get(String id) {
        return mapper.toDomain(repository.findById(id).orElse(null));
    }

    @Override
    public List<DeviceDomain> getAll() {
        return mapper.toDeviceDomain((List<Device>) repository.findAll());
    }

    @Override
    public DeviceDomain update(DeviceDomain deviceDomain) {
        Device device = mapper.toEntity(deviceDomain);

        return mapper.toDomain(repository.save(device));
    }
}
