package com.example.drivewatch.entrypoint.api.facade;

import com.example.drivewatch.core.domain.DeviceDomain;
import com.example.drivewatch.core.usecase.CreateDeviceUseCase;
import com.example.drivewatch.core.usecase.GetAllDevicesUseCase;
import com.example.drivewatch.core.usecase.GetDeviceUseCase;
import com.example.drivewatch.core.usecase.UpdateDeviceUseCase;
import com.example.drivewatch.entrypoint.api.dto.DeviceRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.DeviceResponseDTO;
import com.example.drivewatch.entrypoint.api.mapper.DeviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceFacade {

    private final DeviceMapper mapper;

    private final CreateDeviceUseCase createDeviceUseCase;

    private final GetDeviceUseCase getDeviceUseCase;

    private final GetAllDevicesUseCase getAllDevicesUseCase;

    private final UpdateDeviceUseCase updateDeviceUseCase;

    public DeviceResponseDTO create(DeviceRequestDTO requestDTO) {
        DeviceDomain deviceDomain = mapper.toDomain(requestDTO);

        return mapper.toDto(createDeviceUseCase.create(deviceDomain));
    }

    public DeviceResponseDTO get(String id) {
        return mapper.toDto(getDeviceUseCase.get(id));
    }

    public List<DeviceResponseDTO> getAll() {
        return mapper.toDto(getAllDevicesUseCase.getAll());
    }

    public DeviceResponseDTO update(String id, DeviceRequestDTO requestDTO) {
        DeviceDomain deviceDomain = mapper.toDomain(requestDTO);

        return mapper.toDto(updateDeviceUseCase.update(id, deviceDomain));
    }
}
