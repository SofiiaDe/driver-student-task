package com.test.driverstudenttask.driving.service;

import com.test.driverstudenttask.driving.model.entity.Driver;
import com.test.driverstudenttask.driving.repository.DriverRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;

    public List<Driver> findAll() {
      return driverRepository.findAll();
    }

    public Optional<Driver> findById(Long id) {
      return driverRepository.findById(id);
    }

    public Driver save(Driver driver) {
      return driverRepository.save(driver);
    }

    public void deleteById(Long id) {
      driverRepository.deleteById(id);
    }

}
