package com.example.drivewatch.dataprovider.gateway;

import com.example.drivewatch.core.domain.RegisterDomain;
import com.example.drivewatch.core.gateway.RegisterGateway;
import com.example.drivewatch.dataprovider.database.entity.Register;
import com.example.drivewatch.dataprovider.database.mapper.EntityMapper;
import com.example.drivewatch.dataprovider.database.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterGatewayImpl implements RegisterGateway {

    private final RegisterRepository repository;

    private final EntityMapper mapper;

    @Override
    public RegisterDomain create(RegisterDomain registerDomain) {
        Register register = mapper.toEntity(registerDomain);

        return mapper.toDomain(repository.save(register));
    }

    @Override
    public RegisterDomain get(String id) {

        return mapper.toDomain(repository.findById(id).orElse(null));
    }

    @Override
    public List<RegisterDomain> getAll() {

        return mapper.toRegisterDomain((List<Register>) repository.findAll());
    }

    @Override
    public List<RegisterDomain> getAllByIdDevice(String id) {

        return mapper.toRegisterDomain(repository.findAllByIdDevice(Integer.parseInt(id)));
    }

    @Override
    public RegisterDomain update(RegisterDomain registerDomain) {
        Register register = mapper.toEntity(registerDomain);

        return mapper.toDomain(repository.save(register));
    }
}
