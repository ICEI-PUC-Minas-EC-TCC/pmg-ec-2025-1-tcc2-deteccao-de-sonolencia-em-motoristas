package com.example.drivewatch.dataprovider.gateway;

import com.example.drivewatch.core.domain.PhoneDomain;
import com.example.drivewatch.core.gateway.PhoneGateway;
import com.example.drivewatch.dataprovider.database.entity.Phone;
import com.example.drivewatch.dataprovider.database.mapper.EntityMapper;
import com.example.drivewatch.dataprovider.database.repository.PhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneGatewayImpl implements PhoneGateway {

    private final PhoneRepository repository;

    private final EntityMapper mapper;

    @Override
    public PhoneDomain create(PhoneDomain phoneDomain) {
        Phone phone = mapper.toEntity(phoneDomain);

        return mapper.toDomain(repository.save(phone));
    }

    @Override
    public PhoneDomain get(String id) {
        return mapper.toDomain(repository.findById(id).orElse(null));
    }

    @Override
    public PhoneDomain getByDeviceId(String id) {
        return mapper.toDomain(repository.findByIdDevice(Integer.parseInt(id)));
    }

    @Override
    public PhoneDomain update(PhoneDomain phoneDomain) {
        Phone phone = mapper.toEntity(phoneDomain);

        return mapper.toDomain(repository.save(phone));
    }
}
