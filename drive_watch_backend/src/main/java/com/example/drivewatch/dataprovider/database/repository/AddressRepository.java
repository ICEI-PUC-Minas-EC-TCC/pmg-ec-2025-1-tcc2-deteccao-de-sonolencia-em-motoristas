package com.example.drivewatch.dataprovider.database.repository;

import com.example.drivewatch.dataprovider.database.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, String> {

    public Address findByIdCompany(Integer id);
}
