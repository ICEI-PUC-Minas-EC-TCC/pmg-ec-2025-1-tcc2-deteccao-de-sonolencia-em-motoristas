package com.example.drivewatch.entrypoint.api.controller.impl;

import com.example.drivewatch.entrypoint.api.controller.DeviceController;
import com.example.drivewatch.entrypoint.api.dto.DeviceRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.DeviceResponseDTO;
import com.example.drivewatch.entrypoint.api.facade.DeviceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeviceControllerImpl implements DeviceController {

    private final DeviceFacade facade;

    @Override
    public DeviceResponseDTO create(DeviceRequestDTO deviceRequestDTO) {

        return facade.create(deviceRequestDTO);
    }

    @Override
    public DeviceResponseDTO get(String id) {

        return facade.get(id);
    }

    @Override
    public List<DeviceResponseDTO> getAll() {

        return facade.getAll();
    }

    @Override
    public DeviceResponseDTO update(String id, DeviceRequestDTO deviceRequestDTO) {
        return facade.update(id, deviceRequestDTO);
    }
}
