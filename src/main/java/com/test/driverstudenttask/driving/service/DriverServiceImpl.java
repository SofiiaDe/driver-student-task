package com.test.driverstudenttask.driving.service;

import com.test.driverstudenttask.driving.exception.ResourceNotFoundException;
import com.test.driverstudenttask.driving.model.dto.DriverDto;
import com.test.driverstudenttask.driving.model.entity.Driver;
import com.test.driverstudenttask.driving.model.mapper.DriverMapper;
import com.test.driverstudenttask.driving.model.payload.CreateDriverRequest;
import com.test.driverstudenttask.driving.model.payload.UpdateDriverRequest;
import com.test.driverstudenttask.driving.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;
    private DriverMapper driverMapper;

    @Override
    public DriverDto getDriverById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));
        return driverMapper.toDto(driver);
    }

    @Override
    public Page<DriverDto> getAllDrivers(Pageable pageable) {
        return driverRepository.findAll(pageable).map(driverMapper::toDto);
    }

    @Override
    public DriverDto createDriver(CreateDriverRequest createDriverRequest) {
        Driver driverToSave = Driver.builder()
                .name(createDriverRequest.getName())
                .age(createDriverRequest.getAge())
                .build();
        Driver driver = driverRepository.save(driverToSave);
        return driverMapper.toDto(driver);
    }

    @Override
    public DriverDto updateDriver(Long id, UpdateDriverRequest updateDriverRequest) {
        Driver existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));
        existingDriver.setName(updateDriverRequest.getName());
        existingDriver.setAge(updateDriverRequest.getAge());
        Driver driver = driverRepository.save(existingDriver);
        return driverMapper.toDto(driver);
    }

    @Override
    public void deleteDriver(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));
        driverRepository.delete(driver);
    }

}
