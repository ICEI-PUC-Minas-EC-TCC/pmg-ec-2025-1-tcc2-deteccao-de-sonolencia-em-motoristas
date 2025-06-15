package com.example.drivewatch.entrypoint.api.controller;

import com.example.drivewatch.entrypoint.api.dto.AddressRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.AddressResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/address")
public interface AddressController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    AddressResponseDTO create(
        @Valid @RequestBody final AddressRequestDTO addressRequestDTO
    );

    @GetMapping("/company/{idCompany}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    AddressResponseDTO getByCompanyId(
        @PathVariable @NotBlank final String idCompany
    );

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    AddressResponseDTO get(
        @PathVariable @NotBlank final String id
    );

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    AddressResponseDTO update(
        @PathVariable @NotBlank final String id,
        @Valid @RequestBody final AddressRequestDTO addressRequestDTO
    );
}
