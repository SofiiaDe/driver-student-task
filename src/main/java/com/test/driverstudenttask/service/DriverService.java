package com.test.driverstudenttask.service;

import com.test.driverstudenttask.driving.model.entity.Driver;
import java.util.List;
import java.util.Optional;

public interface DriverService {

  List<Driver> findAll();

  Optional<Driver> findById(Long id);

  Driver save(Driver driver);

  void deleteById(Long id);

}
