package com.example.drivewatch.entrypoint.api.facade;

import com.example.drivewatch.core.domain.RegisterDomain;
import com.example.drivewatch.core.usecase.CreateRegisterUseCase;
import com.example.drivewatch.core.usecase.GetAllRegistersByDeviceIdUseCase;
import com.example.drivewatch.core.usecase.GetAllRegistersUseCase;
import com.example.drivewatch.core.usecase.GetRegisterUseCase;
import com.example.drivewatch.core.usecase.UpdateRegisterUseCase;
import com.example.drivewatch.entrypoint.api.dto.RegisterRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.RegisterResponseDTO;
import com.example.drivewatch.entrypoint.api.mapper.RegisterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegisterFacade {

    private final RegisterMapper mapper;

    private final CreateRegisterUseCase createRegisterUseCase;

    private final GetRegisterUseCase getRegisterUseCase;

    private final GetAllRegistersUseCase getAllRegistersUseCase;

    private final GetAllRegistersByDeviceIdUseCase getAllRegistersByDeviceIdUseCase;

    private final UpdateRegisterUseCase updateRegisterUseCase;


    public RegisterResponseDTO create(RegisterRequestDTO requestDTO) {
        RegisterDomain registerDomain = mapper.toDomain(requestDTO);

        return mapper.toDto(createRegisterUseCase.create(registerDomain));
    }

    public RegisterResponseDTO get(String id) {

        return mapper.toDto(getRegisterUseCase.get(id));
    }

    public List<RegisterResponseDTO> getAll() {

        return mapper.toDto(getAllRegistersUseCase.getAll());
    }

    public List<RegisterResponseDTO> getAllByDeviceId(String id) {

        return mapper.toDto(getAllRegistersByDeviceIdUseCase.getAllByDeviceId(id));
    }

    public RegisterResponseDTO update(String id, RegisterRequestDTO requestDTO) {
        RegisterDomain registerDomain = mapper.toDomain(requestDTO);

        return mapper.toDto(updateRegisterUseCase.update(id, registerDomain));
    }
}
