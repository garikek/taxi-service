package com.software.modsen.driverservice.component;

import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.dto.response.DriverListDto;
import com.software.modsen.driverservice.dto.response.DriverResponse;
import com.software.modsen.driverservice.exception.ResourceNotFoundException;
import com.software.modsen.driverservice.mapper.DriverMapper;
import com.software.modsen.driverservice.model.Driver;
import com.software.modsen.driverservice.repository.DriverRepository;
import com.software.modsen.driverservice.service.impl.DefaultDriverService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.software.modsen.driverservice.util.DriverTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@CucumberContextConfiguration
public class DriverServiceComponentTest {
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private DriverMapper driverMapper;
    @InjectMocks
    private DefaultDriverService driverService;

    private List<DriverResponse> driverResponseList;
    private DriverResponse driverResponse;
    private Exception exception;

    @Given("The driver repository contains multiple drivers")
    public void theDriverRepositoryContainsMultipleDrivers() {
        List<Driver> drivers = List.of(getDefaultDriver(), getUpdatedDriver());
        doReturn(drivers).when(driverRepository).findAll();

        driverResponseList = List.of(getDefaultDriverResponse(), getUpdatedDriverResponse());
        for (Driver driver : drivers) {
            doReturn(driverResponseList.get((int) (driver.getId() - 1)))
                    .when(driverMapper).toDriverDto(driver);
        }
    }

    @When("The getDrivers method is called")
    public void theGetDriversMethodIsCalled() {
        DriverListDto driverListDTO = driverService.getDrivers();
        driverResponseList = driverListDTO.getDriverDtoList();
    }

    @Then("The response should contain a list of all drivers")
    public void theResponseShouldContainAListOfAllDrivers() {
        assertThat(driverResponseList).hasSize(2);
    }

    @Given("A driver with id {long} exists in the repository")
    public void aDriverWithIdExistsInTheRepository(Long id) {
        Driver driver = getDefaultDriver();
        doReturn(Optional.of(driver)).when(driverRepository).findById(id);
        doReturn(getDefaultDriverResponse()).when(driverMapper).toDriverDto(driver);
    }

    @When("The getDriverById method is called with id {long}")
    public void theGetDriverByIdMethodIsCalledWithId(Long id) {
        try {
            driverResponse = driverService.getDriverById(id);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should contain the driver with id {long}")
    public void theResponseShouldContainTheDriverWithId(Long id) {
        assertThat(driverResponse.getId()).isEqualTo(id);
    }

    @Given("A driver with email {string}, phone number {string} and vehicle number {string} doesn't exist")
    public void aDriverWithEmailPhoneVehicleDoesNotExist(String email, String phoneNumber, String vehicleNumber) {
        doReturn(false).when(driverRepository).existsByEmail(email);
        doReturn(false).when(driverRepository).existsByPhoneNumber(phoneNumber);
        doReturn(false).when(driverRepository).existsByVehicleNumber(vehicleNumber);
    }

    @When("A addDriver method is called with name {string}, email {string}, phone number {string}, and vehicle number {string}")
    public void aAddDriverMethodIsCalledWithNameEmailPhoneAndVehicleNumber(String name, String email, String phoneNumber, String vehicleNumber) {
        DriverRequest request = new DriverRequest(name, email, phoneNumber, vehicleNumber);
        Driver driver = Driver.builder()
                .id(null)
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .vehicleNumber(vehicleNumber)
                .build();
        doReturn(driver).when(driverMapper).toDriverEntity(request);
        doReturn(driver).when(driverRepository).save(driver);
        doReturn(new DriverResponse(1L, name, email, phoneNumber, vehicleNumber)).when(driverMapper).toDriverDto(driver);

        driverResponse = driverService.addDriver(request);
    }

    @Then("The response should contain the created driver with name {string}, email {string}, phone number {string} and vehicle number {string}")
    public void theResponseShouldContainTheCreatedDriverWithNameEmailPhoneAndVehicleNumber(String name, String email, String phone, String vehicleNumber) {
        assertThat(driverResponse.getName()).isEqualTo(name);
        assertThat(driverResponse.getEmail()).isEqualTo(email);
        assertThat(driverResponse.getPhoneNumber()).isEqualTo(phone);
        assertThat(driverResponse.getVehicleNumber()).isEqualTo(vehicleNumber);
    }

    @When("An updateDriver method is called with id {long}, name {string}, email {string}, phone {string}, and vehicle number {string}")
    public void anUpdateDriverMethodIsCalledWithIdEmailPhoneAndVehicleNumber(Long id, String name, String email, String phone, String vehicleNumber) {
        DriverRequest request = new DriverRequest(name, email, phone, vehicleNumber);
        Driver driver = getDefaultDriver();
        doReturn(Optional.of(driver)).when(driverRepository).findById(id);
        doReturn(driver).when(driverMapper).toDriverEntity(request);
        doReturn(driver).when(driverRepository).save(driver);
        doReturn(new DriverResponse(id, name, email, phone, vehicleNumber)).when(driverMapper).toDriverDto(driver);

        driverResponse = driverService.updateDriver(id, request);
    }

    @Then("The response should contain the updated driver with id {long}, name {string}, email {string}, phone number {string} and vehicle number {string}")
    public void theResponseShouldContainTheUpdatedDriverWithIdEmailPhoneAndVehicleNumber(Long id, String name, String email, String phone, String vehicleNumber) {
        assertThat(driverResponse.getId()).isEqualTo(id);
        assertThat(driverResponse.getName()).isEqualTo(name);
        assertThat(driverResponse.getEmail()).isEqualTo(email);
        assertThat(driverResponse.getPhoneNumber()).isEqualTo(phone);
        assertThat(driverResponse.getVehicleNumber()).isEqualTo(vehicleNumber);
    }

    @Given("A driver with id {long} doesn't exist")
    public void aDriverWithIdDoesNotExist(Long id) {
        doReturn(Optional.empty()).when(driverRepository).findById(id);
    }

    @When("The id {long} is passed to the deleteDriver method")
    public void theIdIsPassedToTheDeleteDriverMethod(long id) {
        try {
            driverService.deleteDriver(id);
        } catch (ResourceNotFoundException e) {
            exception = e;
        }
    }

    @Then("The ResourceNotFoundException with id {long} should be thrown")
    public void theDriverNotFoundExceptionWithIdShouldBeThrown(Long id) {
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
        assertThat(exception.getMessage()).contains("Driver not found - " + id);
    }
}
