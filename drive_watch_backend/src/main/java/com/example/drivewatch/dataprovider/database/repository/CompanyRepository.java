package com.example.drivewatch.dataprovider.database.repository;

import com.example.drivewatch.dataprovider.database.entity.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, String> {
}
