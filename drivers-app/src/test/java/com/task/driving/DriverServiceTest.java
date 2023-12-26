package com.task.driving;

import com.task.driving.exception.ResourceNotFoundException;
import com.task.driving.model.dto.DriverDto;
import com.task.driving.model.entity.Driver;
import com.task.driving.model.mapper.DriverMapper;
import com.task.driving.model.payload.CreateDriverRequest;
import com.task.driving.model.payload.UpdateDriverRequest;
import com.task.driving.repository.DriverRepository;
import com.task.driving.service.DriverService;
import com.task.driving.service.DriverServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverMapper driverMapper;

    private DriverService driverService;

    @BeforeEach
    public void setUp() {
        driverService = new DriverServiceImpl(driverRepository, driverMapper);
    }

    @Test
    void getDriverById_CorrectResult() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(createSampleDriver()));
        when(driverMapper.toDto(any())).thenReturn(createSampleDriverDto());

        DriverDto result = driverService.getDriverById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Tom Ford", result.getName());

        verify(driverRepository, times(1)).findById(eq(1L));
        verify(driverMapper, times(1)).toDto(any());
    }

    @Test
    void getDriverById_NotFound() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> driverService.getDriverById(1L));
        assertEquals("Driver not found with id : '1'", exception.getMessage());

        verify(driverRepository, times(1)).findById(eq(1L));
        verify(driverMapper, never()).toDto(any());
    }

    @Test
    void getAllDrivers_PositiveScenario() {
        when(driverRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(createSampleDriver())));
        when(driverMapper.toDto(any())).thenReturn(createSampleDriverDto());

        Page<DriverDto> result = driverService.getAllDrivers(mock(Pageable.class));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());

        verify(driverRepository, times(1)).findAll(any(Pageable.class));
        verify(driverMapper, times(1)).toDto(any());
    }

    @Test
    void createDriver_PositiveScenario() {
        when(driverRepository.save(any())).thenReturn(createSampleDriver());
        when(driverMapper.toDto(any())).thenReturn(createSampleDriverDto());

        CreateDriverRequest createDriverRequest = CreateDriverRequest.builder()
                .name("Tom Ford")
                .age(30)
                .build();
        DriverDto result = driverService.createDriver(createDriverRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Tom Ford", result.getName());
        assertEquals(30, result.getAge());

        verify(driverRepository, times(1)).save(any());
        verify(driverMapper, times(1)).toDto(any());
    }

    @Test
    void updateDriver_NegativeScenario_NotFound() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> driverService.updateDriver(1L, new UpdateDriverRequest()));
        assertEquals("Driver not found with id : '1'", exception.getMessage());

        verify(driverRepository, times(1)).findById(eq(1L));
        verify(driverRepository, never()).save(any());
        verify(driverMapper, never()).toDto(any());
    }

    @Test
    void updateDriver_PositiveScenario() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(createSampleDriver()));

        UpdateDriverRequest updateDriverRequest = UpdateDriverRequest.builder()
                .name("Updated Driver")
                .age(28)
                .build();

        Driver updatedDriver = Driver.builder()
                .id(1L)
                .name("Updated Driver")
                .age(28)
                .build();

        DriverDto updatedDriverDto = DriverDto.builder()
                .id(1L)
                .name("Updated Driver")
                .age(28)
                .build();

        when(driverRepository.save(any())).thenReturn(updatedDriver);
        when(driverMapper.toDto(any())).thenReturn(updatedDriverDto);

        DriverDto result = driverService.updateDriver(1L, updateDriverRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Driver", result.getName());
        assertEquals(28, result.getAge());

        verify(driverRepository, times(1)).findById(eq(1L));
        verify(driverRepository, times(1)).save(any());
        verify(driverMapper, times(1)).toDto(any());
    }

    @Test
    void deleteDriver_NotFound() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> driverService.deleteDriver(1L));
        assertEquals("Driver not found with id : '1'", exception.getMessage());

        verify(driverRepository, times(1)).findById(eq(1L));
        verify(driverRepository, never()).delete(any());
    }

    @Test
    void deleteDriver_correctResult() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(createSampleDriver()));
        assertDoesNotThrow(() -> driverService.deleteDriver(1L));

        verify(driverRepository, times(1)).findById(eq(1L));
        verify(driverRepository, times(1)).delete(any());
    }

    private Driver createSampleDriver() {
        return Driver.builder()
                .id(1L)
                .name("Tom Ford")
                .age(30)
                .build();
    }

    private DriverDto createSampleDriverDto() {
        return DriverDto.builder()
                .id(1L)
                .name("Tom Ford")
                .age(30)
                .build();
    }
}