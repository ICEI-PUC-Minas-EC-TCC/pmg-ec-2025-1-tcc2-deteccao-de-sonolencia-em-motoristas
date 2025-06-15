package com.example.drivewatch.entrypoint.api.controller.impl;

import com.example.drivewatch.entrypoint.api.controller.AddressController;
import com.example.drivewatch.entrypoint.api.dto.AddressRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.AddressResponseDTO;
import com.example.drivewatch.entrypoint.api.facade.AddressFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressControllerImpl implements AddressController {

    private final AddressFacade facade;

    @Override
    public AddressResponseDTO create(AddressRequestDTO addressRequestDTO) {

        return facade.create(addressRequestDTO);
    }

    @Override
    public AddressResponseDTO getByCompanyId(String idCompany) {

        return facade.getByCompanyId(idCompany);
    }

    @Override
    public AddressResponseDTO get(String id) {

        return facade.get(id);
    }

    @Override
    public AddressResponseDTO update(String id, AddressRequestDTO addressRequestDTO) {

        return facade.update(id, addressRequestDTO);
    }
}
