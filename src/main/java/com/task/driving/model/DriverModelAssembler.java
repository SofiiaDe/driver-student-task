package com.task.driving.model;

import com.task.driving.controller.DriverController;
import com.task.driving.model.dto.DriverDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DriverModelAssembler implements RepresentationModelAssembler<DriverDto, EntityModel<DriverDto>> {

    /**
     * Converts DriverDto objects to EntityModel<DriverDto> objects.
     *
     * @param driverDto Object of DriverDto type
     * @return EntityModel<DriverDto>
     */
    @Override
    public EntityModel<DriverDto> toModel(DriverDto driverDto) {

        return EntityModel.of(driverDto,
                linkTo(methodOn(DriverController.class).getDriverById(driverDto.getId())).withSelfRel(),
                linkTo(methodOn(DriverController.class).getAllDrivers(null)).withRel("users"));
    }

}