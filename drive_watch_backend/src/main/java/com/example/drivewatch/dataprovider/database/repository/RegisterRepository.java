package com.example.drivewatch.dataprovider.database.repository;

import com.example.drivewatch.dataprovider.database.entity.Register;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegisterRepository  extends CrudRepository<Register, String> {

    public List<Register> findAllByIdDevice(Integer id);
}
