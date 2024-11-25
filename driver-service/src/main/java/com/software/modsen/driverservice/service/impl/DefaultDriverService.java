package com.software.modsen.driverservice.service.impl;

import com.software.modsen.driverservice.dto.response.DriverListDto;
import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.dto.response.DriverResponse;
import com.software.modsen.driverservice.exception.ResourceNotFoundException;
import com.software.modsen.driverservice.exception.InvalidResourceException;
import com.software.modsen.driverservice.exception.DuplicateResourceException;
import com.software.modsen.driverservice.mapper.DriverMapper;
import com.software.modsen.driverservice.model.Driver;
import com.software.modsen.driverservice.repository.DriverRepository;
import com.software.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.software.modsen.driverservice.utility.Constant.DRIVER_NOT_FOUND_BY_ID;
import static com.software.modsen.driverservice.utility.Constant.INVALID_EMAIL;
import static com.software.modsen.driverservice.utility.Constant.INVALID_PHONE_NUMBER;
import static com.software.modsen.driverservice.utility.Constant.INVALID_VEHICLE_NUMBER;
import static com.software.modsen.driverservice.utility.Constant.DUPLICATE_EMAIL;
import static com.software.modsen.driverservice.utility.Constant.DUPLICATE_PHONE_NUMBER;
import static com.software.modsen.driverservice.utility.Constant.DUPLICATE_VEHICLE_NUMBER;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultDriverService implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+375\\d{9}$");
    private static final Pattern VEHICLE_NUMBER_PATTERN = Pattern.compile("^\\d{4}[A-Z]{2}[1-7]$");

    @Override
    public DriverListDto getDrivers() {
        return new DriverListDto(driverRepository.findAll().stream()
                .map(driverMapper::toDriverDto)
                .collect(Collectors.toList()));
    }

    @Override
    public DriverResponse addDriver(DriverRequest driverRequest) {
        Driver driver = driverMapper.toDriverEntity(driverRequest);
        validateAddDriver(driver);
        Driver savedDriver = driverRepository.save(driver);
        return driverMapper.toDriverDto(savedDriver);
    }

    @Override
    public DriverResponse getDriverById(Long id) {
        return driverMapper.toDriverDto(getByIdOrThrow(id));
    }

    @Override
    public void deleteDriver(Long id) {
        if(!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(DRIVER_NOT_FOUND_BY_ID, id));
        }
        driverRepository.deleteById(id);
    }

    @Override
    public DriverResponse updateDriver(Long id, DriverRequest driverRequest) {
        Driver existingDriver = getByIdOrThrow(id);
        driverMapper.updateDriverFromDto(driverRequest, existingDriver);
        validateUpdateDriver(existingDriver);
        existingDriver.setUpdatedAt(LocalDateTime.now());
        Driver updatedDriver = driverRepository.save(existingDriver);
        return driverMapper.toDriverDto(updatedDriver);
    }

    private void validateAddDriver(Driver driver){
        validateEmail(driver);
        validatePhoneNumber(driver);
        validateVehicleNumber(driver);
        checkDuplicateEmail(driver);
        checkDuplicatePhoneNumber(driver);
        checkDuplicateVehicleNumber(driver);
    }

    private void validateUpdateDriver(Driver driver){
        validateEmail(driver);
        validatePhoneNumber(driver);
        validateVehicleNumber(driver);
        checkDuplicateEmail(driver);
        checkDuplicatePhoneNumber(driver);
        checkDuplicateVehicleNumber(driver);
    }

    private void validateEmail (Driver driver){
        if (!EMAIL_PATTERN.matcher(driver.getEmail()).matches()) {
            throw new InvalidResourceException(String.format(INVALID_EMAIL, driver.getEmail()));
        }
    }

    private void validatePhoneNumber (Driver driver){
        if (!PHONE_NUMBER_PATTERN.matcher(driver.getPhoneNumber()).matches()) {
            throw new InvalidResourceException(String.format(INVALID_PHONE_NUMBER, driver.getPhoneNumber()));
        }
    }

    private void validateVehicleNumber(Driver driver){
        if (!VEHICLE_NUMBER_PATTERN.matcher(driver.getVehicleNumber()).matches()) {
            throw new InvalidResourceException(String.format(INVALID_VEHICLE_NUMBER, driver.getVehicleNumber()));
        }
    }

    private void checkDuplicateEmail (Driver driver){
        driverRepository.findByEmail(driver.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(driver.getId())) {
                throw new DuplicateResourceException(String.format(DUPLICATE_EMAIL, driver.getEmail()));
            }
        });
    }

    private void checkDuplicatePhoneNumber (Driver driver){
        driverRepository.findByPhoneNumber(driver.getPhoneNumber()).ifPresent(existing -> {
            if (!existing.getId().equals(driver.getId())) {
                throw new DuplicateResourceException(String.format(DUPLICATE_PHONE_NUMBER, driver.getPhoneNumber()));
            }
        });
    }

    private void checkDuplicateVehicleNumber(Driver driver){
        driverRepository.findByVehicleNumber(driver.getVehicleNumber()).ifPresent(existing -> {
            if (!existing.getId().equals(driver.getId())) {
                throw new DuplicateResourceException(String.format(DUPLICATE_VEHICLE_NUMBER, driver.getVehicleNumber()));
            }
        });
    }

    private Driver getByIdOrThrow(Long id){
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DRIVER_NOT_FOUND_BY_ID, id)));
    }
}
