package com.task.driving.controller;

import com.task.driving.model.DriverModelAssembler;
import com.task.driving.model.payload.CreateDriverRequest;
import com.task.driving.model.dto.DriverDto;
import com.task.driving.model.payload.UpdateDriverRequest;
import com.task.driving.service.DriverService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/drivers")
@AllArgsConstructor
public class DriverController extends BaseController {

    private DriverService driverService;
    private PagedResourcesAssembler<DriverDto> pagedResourcesAssembler;
    private DriverModelAssembler driverModelAssembler;
    private static final int PAGE_SIZE_DEFAULT = 5;

    @GetMapping("/{id}")
    public EntityModel<DriverDto> getDriverById(@PathVariable Long id) {
        DriverDto driverDto = driverService.getDriverById(id);
        return EntityModel.of(driverDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DriverController.class)
                        .getDriverById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DriverController.class)
                        .getAllDrivers(null)).withRel("drivers"));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DriverDto>>> getAllDrivers(
            @PageableDefault(size = PAGE_SIZE_DEFAULT) Pageable pageable) {
        Page<DriverDto> drivers = driverService.getAllDrivers(pageable);
        PagedModel<EntityModel<DriverDto>> pagedModel = pagedResourcesAssembler
                .toModel(drivers, driverModelAssembler);
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping
    public ResponseEntity<?> createDriver(@Valid @RequestBody CreateDriverRequest createDriverRequest,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        DriverDto driver = driverService.createDriver(createDriverRequest);
        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DriverController.class)
                        .getDriverById(driver.getId())).toUri())
                .body(EntityModel.of(driver,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DriverController.class)
                                .getDriverById(driver.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DriverController.class)
                                .getAllDrivers(null)).withRel("drivers")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable Long id,
                                          @Valid @RequestBody UpdateDriverRequest updateDriverRequest,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }
        DriverDto driver = driverService.updateDriver(id, updateDriverRequest);
        return ResponseEntity.ok(EntityModel.of(driver,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DriverController.class)
                        .getDriverById(driver.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DriverController.class)
                        .getAllDrivers(null)).withRel("drivers")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}