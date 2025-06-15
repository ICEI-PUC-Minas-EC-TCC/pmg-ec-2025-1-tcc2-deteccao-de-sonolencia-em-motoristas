package com.example.drivewatch.entrypoint.api.controller.impl;

import com.example.drivewatch.entrypoint.api.controller.CompanyController;
import com.example.drivewatch.entrypoint.api.dto.CompanyDTO;
import com.example.drivewatch.entrypoint.api.dto.CompanyRequestDTO;
import com.example.drivewatch.entrypoint.api.facade.CompanyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompanyControllerImpl implements CompanyController {

    private final CompanyFacade facade;

    @Override
    public CompanyDTO create(CompanyRequestDTO companyRequestDTO) {

        return facade.create(companyRequestDTO);
    }

    @Override
    public List<CompanyDTO> getAll() {
        return facade.getAll();
    }

    @Override
    public CompanyDTO get(String id) {
        return facade.get(id);
    }

    @Override
    public CompanyDTO update(String id, CompanyRequestDTO companyRequestDTO) {
        return facade.update(id, companyRequestDTO);
    }
}
