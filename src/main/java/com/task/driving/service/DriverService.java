package com.task.driving.service;

import com.task.driving.model.payload.CreateDriverRequest;
import com.task.driving.model.dto.DriverDto;
import com.task.driving.model.payload.UpdateDriverRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {

    DriverDto getDriverById(Long id);

    Page<DriverDto> getAllDrivers(Pageable pageable);

    DriverDto createDriver(CreateDriverRequest createDriverRequest);

    DriverDto updateDriver(Long id, UpdateDriverRequest updateDriverRequest);

    void deleteDriver(Long id);

}
