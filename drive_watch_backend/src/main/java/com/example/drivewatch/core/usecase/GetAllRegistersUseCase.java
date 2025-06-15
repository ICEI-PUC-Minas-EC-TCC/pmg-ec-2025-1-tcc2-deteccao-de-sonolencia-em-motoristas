package com.example.drivewatch.core.usecase;

import com.example.drivewatch.core.domain.RegisterDomain;

import java.util.List;

public interface GetAllRegistersUseCase {

    List<RegisterDomain> getAll();
}
