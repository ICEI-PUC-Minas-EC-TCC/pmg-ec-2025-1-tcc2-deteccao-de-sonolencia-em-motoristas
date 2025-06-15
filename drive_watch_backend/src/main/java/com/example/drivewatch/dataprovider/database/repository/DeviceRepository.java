package com.example.drivewatch.dataprovider.database.repository;

import com.example.drivewatch.dataprovider.database.entity.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository  extends CrudRepository<Device, String> {
}
