package com.task.driving;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.driving.controller.DriverController;
import com.task.driving.exception.ResourceNotFoundException;
import com.task.driving.model.DriverModelAssembler;
import com.task.driving.model.dto.DriverDto;
import com.task.driving.model.payload.CreateDriverRequest;
import com.task.driving.model.payload.UpdateDriverRequest;
import com.task.driving.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DriverControllerTest {
    
    private static final String DRIVERS_API = "/drivers";

    private MockMvc mockMvc;

    @Mock
    private DriverService driverService;

    @Mock
    private PagedResourcesAssembler<DriverDto> pagedResourcesAssembler;

    @Mock
    private DriverModelAssembler driverModelAssembler;

    @InjectMocks
    private DriverController driverController;

    private ObjectMapper objectMapper;
    private Pageable pageable;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(driverController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
        pageable = PageRequest.of(0, 5);
    }

    @Test
    void getDriverById_CorrectResult() throws Exception {
        when(driverService.getDriverById(anyLong())).thenReturn(createSampleDriverDto());

        mockMvc.perform(MockMvcRequestBuilders.get(DRIVERS_API + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tom Ford"));

        verify(driverService, times(1)).getDriverById(eq(1L));
    }

    @Test
    void getDriverById_NotFound() throws Exception {
        when(driverService.getDriverById(anyLong())).thenThrow(new ResourceNotFoundException("Driver", "id", anyLong()));

        mockMvc.perform(MockMvcRequestBuilders.get(DRIVERS_API + "/{id}", 1))
                .andExpect(status().isNotFound());

        verify(driverService, times(1)).getDriverById(eq(1L));
    }

    @Test
    void getAllDrivers_CorrectResult() throws Exception {
        when(driverService.getAllDrivers(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(createSampleDriverDto())));

        mockMvc.perform(MockMvcRequestBuilders.get(DRIVERS_API))
                .andExpect(status().isOk());

        verify(driverService, times(1)).getAllDrivers(any(Pageable.class));
    }

    @Test
    void createDriver_InvalidRequest() throws Exception {
        CreateDriverRequest invalidRequest = CreateDriverRequest.builder()
                .name(null)
                .age(null)
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(DRIVERS_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    Map<String, String> errors = objectMapper.readValue(response, new TypeReference<>() {
                    });
                    assertEquals("Name is required", errors.get("name"));
                    assertEquals("Age is required", errors.get("age"));
                });

        verify(driverService, never()).createDriver(any(CreateDriverRequest.class));
    }

    @Test
    void updateDriver_InvalidRequest() throws Exception {
        UpdateDriverRequest invalidRequest = UpdateDriverRequest.builder()
                .name(null)
                .age(null)
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/drivers/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    Map<String, String> errors = objectMapper.readValue(response, new TypeReference<>() {
                    });
                    assertEquals("Name is required", errors.get("name"));
                    assertEquals("Age is required", errors.get("age"));
                });

        verify(driverService, never()).updateDriver(anyLong(), any(UpdateDriverRequest.class));
    }

    @Test
    void deleteDriver_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(DRIVERS_API + "/{id}", 1))
                .andExpect(status().isNoContent());

        verify(driverService, times(1)).deleteDriver(eq(1L));
    }

    private DriverDto createSampleDriverDto() {
        DriverDto driverDto = new DriverDto();
        driverDto.setId(1L);
        driverDto.setName("Tom Ford");
        driverDto.setAge(27);
        return driverDto;
    }

}