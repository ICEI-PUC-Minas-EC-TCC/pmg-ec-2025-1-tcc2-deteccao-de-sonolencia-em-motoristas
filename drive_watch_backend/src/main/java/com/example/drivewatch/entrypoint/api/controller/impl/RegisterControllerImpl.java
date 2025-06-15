package com.example.drivewatch.entrypoint.api.controller.impl;

import com.example.drivewatch.entrypoint.api.controller.RegisterController;
import com.example.drivewatch.entrypoint.api.dto.RegisterRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.RegisterResponseDTO;
import com.example.drivewatch.entrypoint.api.facade.RegisterFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegisterControllerImpl implements RegisterController {

    private final RegisterFacade facade;

    @Override
    public RegisterResponseDTO create(RegisterRequestDTO requestDTO) {

        return facade.create(requestDTO);
    }

    @Override
    public RegisterResponseDTO get(String id) {

        return facade.get(id);
    }

    @Override
    public List<RegisterResponseDTO> getAllByIdDevice(String id) {

        return facade.getAllByDeviceId(id);
    }

    @Override
    public List<RegisterResponseDTO> getAll() {

        return facade.getAll();
    }

    @Override
    public RegisterResponseDTO update(String id, RegisterRequestDTO requestDTO) {

        return facade.update(id, requestDTO);
    }
}
