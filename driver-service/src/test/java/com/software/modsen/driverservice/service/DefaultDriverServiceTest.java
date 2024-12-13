package com.software.modsen.driverservice.service;

import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.dto.response.DriverResponse;
import com.software.modsen.driverservice.exception.DuplicateResourceException;
import com.software.modsen.driverservice.exception.InvalidResourceException;
import com.software.modsen.driverservice.exception.ResourceNotFoundException;
import com.software.modsen.driverservice.mapper.DriverMapper;
import com.software.modsen.driverservice.model.Driver;
import com.software.modsen.driverservice.repository.DriverRepository;
import com.software.modsen.driverservice.service.impl.DefaultDriverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static com.software.modsen.driverservice.util.DriverTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDriverServiceTest {
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private DriverMapper driverMapper;
    @InjectMocks
    private DefaultDriverService driverService;

    @Test
    void testGetDriverById_Success() {
        Driver driver = getDefaultDriver();

        when(driverRepository.findById(DEFAULT_DRIVER_ID)).thenReturn(Optional.of(driver));
        when(driverMapper.toDriverDto(driver)).thenReturn(getDefaultDriverResponse());

        DriverResponse response = driverService.getDriverById(DEFAULT_DRIVER_ID);

        assertEquals(DEFAULT_NAME, response.getName());
        verify(driverRepository, times(1)).findById(DEFAULT_DRIVER_ID);
    }

    @Test
    void testGetDriverById_NotFound() {
        when(driverRepository.findById(DEFAULT_DRIVER_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> driverService.getDriverById(DEFAULT_DRIVER_ID));
    }

    @Test
    void testAddDriver_Success() {
        DriverRequest request = getDefaultDriverRequest();
        Driver driver = getDefaultDriver();
        Driver savedDriver = getDefaultDriver();

        when(driverMapper.toDriverEntity(request)).thenReturn(driver);
        when(driverRepository.save(driver)).thenReturn(savedDriver);
        when(driverMapper.toDriverDto(savedDriver)).thenReturn(getDefaultDriverResponse());

        DriverResponse response = driverService.addDriver(request);

        assertEquals(DEFAULT_NAME, response.getName());
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void testAddDriver_InvalidEmail() {
        DriverRequest request = getDefaultDriverRequestWithInvalidEmail();

        when(driverMapper.toDriverEntity(request)).thenReturn(getDriverWithInvalidEmail());

        InvalidResourceException exception = assertThrows(InvalidResourceException.class,
                () -> driverService.addDriver(request));
        assertEquals("Invalid email: " + INVALID_EMAIL, exception.getMessage());
    }

    @Test
    void testAddDriver_InvalidPhoneNumber() {
        DriverRequest request = getDefaultDriverRequestWithInvalidPhoneNumber();

        when(driverMapper.toDriverEntity(request)).thenReturn(getDriverWithInvalidPhoneNumber());

        InvalidResourceException exception = assertThrows(InvalidResourceException.class,
                () -> driverService.addDriver(request));
        assertEquals("Invalid phone number: " + INVALID_PHONE_NUMBER, exception.getMessage());
    }

    @Test
    void testAddDriver_InvalidVehicleNumber() {
        DriverRequest request = getDefaultDriverRequestWithInvalidVehicleNumber();

        when(driverMapper.toDriverEntity(request)).thenReturn(getDriverWithInvalidVehicleNumber());

        InvalidResourceException exception = assertThrows(InvalidResourceException.class,
                () -> driverService.addDriver(request));
        assertEquals("Invalid vehicle number: " + INVALID_VEHICLE_NUMBER, exception.getMessage());
    }

    @Test
    void testAddDriver_DuplicateEmail() {
        DriverRequest request = getDefaultDriverRequest();

        when(driverMapper.toDriverEntity(request)).thenReturn(getDefaultDriver());
        when(driverRepository.findByEmail(DEFAULT_EMAIL)).thenReturn(Optional.of(getDriverWithEmailOnly()));

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> driverService.addDriver(request));
        assertEquals("Email " + DEFAULT_EMAIL + " exists already", exception.getMessage());
    }

    @Test
    void testAddDriver_DuplicatePhoneNumber() {
        DriverRequest request = getDefaultDriverRequest();

        when(driverMapper.toDriverEntity(request)).thenReturn(getDefaultDriver());
        when(driverRepository.findByPhoneNumber(DEFAULT_PHONE_NUMBER)).thenReturn(Optional.of(getDriverWithPhoneNumberOnly()));

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> driverService.addDriver(request));
        assertEquals("Phone number " + DEFAULT_PHONE_NUMBER + " exists already", exception.getMessage());
    }

    @Test
    void testAddDriver_DuplicateVehicleNumber() {
        DriverRequest request = getDefaultDriverRequest();

        when(driverMapper.toDriverEntity(request)).thenReturn(getDefaultDriver());
        when(driverRepository.findByVehicleNumber(DEFAULT_VEHICLE_NUMBER)).thenReturn(Optional.of(getDriverWithVehicleNumberOnly()));

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> driverService.addDriver(request));
        assertEquals("Vehicle number " + DEFAULT_VEHICLE_NUMBER + " exists already", exception.getMessage());
    }

    @Test
    void testUpdateDriver_Success() {
        Driver existingDriver = getDefaultDriver();
        DriverRequest updateRequest = getUpdatedDriverRequest();
        Driver updatedDriver = getUpdatedDriver();

        when(driverRepository.findById(DEFAULT_DRIVER_ID)).thenReturn(Optional.of(existingDriver));
        doNothing().when(driverMapper).updateDriverFromDto(updateRequest, existingDriver);
        when(driverRepository.save(existingDriver)).thenReturn(updatedDriver);
        when(driverMapper.toDriverDto(updatedDriver)).thenReturn(getUpdatedDriverResponse());

        DriverResponse response = driverService.updateDriver(DEFAULT_DRIVER_ID, updateRequest);

        assertEquals(UPDATED_NAME, response.getName());
        verify(driverRepository, times(1)).save(existingDriver);
    }

    @Test
    void testDeleteDriver_Success() {
        when(driverRepository.existsById(DEFAULT_DRIVER_ID)).thenReturn(true);

        driverService.deleteDriver(DEFAULT_DRIVER_ID);

        verify(driverRepository, times(1)).deleteById(DEFAULT_DRIVER_ID);
    }

    @Test
    void testDeleteDriver_NotFound() {
        when(driverRepository.existsById(DEFAULT_DRIVER_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> driverService.deleteDriver(DEFAULT_DRIVER_ID));
    }
}
