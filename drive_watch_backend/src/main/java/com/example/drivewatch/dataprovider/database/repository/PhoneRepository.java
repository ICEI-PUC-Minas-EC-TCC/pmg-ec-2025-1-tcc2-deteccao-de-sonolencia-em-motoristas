package com.example.drivewatch.dataprovider.database.repository;

import com.example.drivewatch.dataprovider.database.entity.Phone;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<Phone, String> {

    public Phone findByIdDevice(Integer id);
}
