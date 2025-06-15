package com.example.drivewatch.entrypoint.api.facade;

import com.example.drivewatch.core.domain.AddressDomain;
import com.example.drivewatch.core.usecase.CreateAddressUseCase;
import com.example.drivewatch.core.usecase.GetAddressByCompanyIdUseCase;
import com.example.drivewatch.core.usecase.GetAddressUseCase;
import com.example.drivewatch.core.usecase.UpdateAddressUseCase;
import com.example.drivewatch.entrypoint.api.dto.AddressRequestDTO;
import com.example.drivewatch.entrypoint.api.dto.AddressResponseDTO;
import com.example.drivewatch.entrypoint.api.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressFacade {

    private final AddressMapper mapper;

    private final CreateAddressUseCase createAddressUseCase;

    private final GetAddressByCompanyIdUseCase getAddressByCompanyIdUseCase;

    private final GetAddressUseCase getAddressUseCase;

    private final UpdateAddressUseCase updateAddressUseCase;

    public AddressResponseDTO create(AddressRequestDTO requestDTO) {
        AddressDomain addressDomain = mapper.toDomain(requestDTO);

        return mapper.toDto(createAddressUseCase.create(addressDomain));
    }

    public AddressResponseDTO getByCompanyId(String idCompany) {

        return mapper.toDto(getAddressByCompanyIdUseCase.getByCompanyId(idCompany));
    }

    public AddressResponseDTO get(String id) {

        return mapper.toDto(getAddressUseCase.get(id));
    }

    public AddressResponseDTO update(String id, AddressRequestDTO requestDTO) {
        AddressDomain addressDomain = mapper.toDomain(requestDTO);

        return mapper.toDto(updateAddressUseCase.update(id, addressDomain));
    }
}
