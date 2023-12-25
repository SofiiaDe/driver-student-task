package com.task.driving.model.mapper;

import com.task.driving.model.dto.DriverDto;
import com.task.driving.model.entity.Driver;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class DriverMapper implements EntityMapper<DriverDto, Driver> {

  public abstract Driver toEntity(DriverDto dto);

  public abstract DriverDto toDto(Driver entity);

}