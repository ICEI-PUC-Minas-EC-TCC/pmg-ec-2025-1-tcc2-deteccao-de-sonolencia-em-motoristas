package com.example.drivewatch.entrypoint.api.facade;

import com.example.drivewatch.core.domain.PhoneDomain;
import com.example.drivewatch.core.usecase.CreatePhoneUseCase;
import com.example.drivewatch.core.usecase.GetPhoneByDeviceIdUseCase;
import com.example.drivewatch.core.usecase.GetPhoneUseCase;
import com.example.drivewatch.core.usecase.UpdatePhoneUseCase;
import com.example.drivewatch.entrypoint.api.dto.PhoneRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.PhoneResponseDTO;
import com.example.drivewatch.entrypoint.api.mapper.PhoneMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PhoneFacade {

    private final PhoneMapper mapper;

    private final CreatePhoneUseCase createPhoneUseCase;

    private final GetPhoneUseCase getPhoneUseCase;

    private final GetPhoneByDeviceIdUseCase getPhoneByDeviceIdUseCase;

    private final UpdatePhoneUseCase updatePhoneUseCase;

    public PhoneResponseDTO create(PhoneRequestDTO requestDTO) {
        PhoneDomain phoneDomain = mapper.toDomain(requestDTO);

        return mapper.toDto(createPhoneUseCase.create(phoneDomain));
    }

    public PhoneResponseDTO get(String id) {

        return mapper.toDto(getPhoneUseCase.get(id));
    }

    public PhoneResponseDTO getByIdDevice(String id) {

        return mapper.toDto(getPhoneByDeviceIdUseCase.getByIdDevice(id));
    }

    public PhoneResponseDTO update(String id, PhoneRequestDTO requestDTO) {
        PhoneDomain phoneDomain = mapper.toDomain(requestDTO);

        return mapper.toDto(updatePhoneUseCase.update(id, phoneDomain));
    }
}
