package com.software.modsen.passengerservice.component;

import com.software.modsen.passengerservice.dto.request.PassengerRequest;
import com.software.modsen.passengerservice.dto.response.PassengerListDTO;
import com.software.modsen.passengerservice.dto.response.PassengerResponse;
import com.software.modsen.passengerservice.exception.ResourceNotFoundException;
import com.software.modsen.passengerservice.mapper.PassengerMapper;
import com.software.modsen.passengerservice.model.Passenger;
import com.software.modsen.passengerservice.repository.PassengerRepository;
import com.software.modsen.passengerservice.service.impl.DefaultPassengerService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.software.modsen.passengerservice.util.PassengerTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@CucumberContextConfiguration
public class PassengerServiceComponentTest {
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private PassengerMapper passengerMapper;
    @InjectMocks
    private DefaultPassengerService passengerService;

    private List<PassengerResponse> passengerResponseList;
    private PassengerResponse passengerResponse;
    private Exception exception;

    @Given("The passenger repository contains multiple passengers")
    public void thePassengerRepositoryContainsMultiplePassengers() {
        List<Passenger> passengers = List.of(getDefaultPassenger(), getUpdatedPassenger());
        doReturn(passengers).when(passengerRepository).findAll();

        passengerResponseList = List.of(getDefaultPassengerResponse(), getUpdatedPassengerResponse());
        for (Passenger passenger : passengers) {
            doReturn(passengerResponseList.get((int) (passenger.getId() - 1)))
                    .when(passengerMapper).toPassengerDTO(passenger);
        }
    }

    @When("The getPassengers method is called")
    public void theGetPassengersMethodIsCalled() {
        PassengerListDTO passengerListDTO = passengerService.getPassengers();
        passengerResponseList = passengerListDTO.getPassengerDTOList();
    }

    @Then("The response should contain a list of all passengers")
    public void theResponseShouldContainAListOfAllPassengers() {
        assertThat(passengerResponseList).hasSize(2);
    }

    @Given("A passenger with id {long} exists in the repository")
    public void aPassengerWithIdExistsInTheRepository(Long id) {
        Passenger passenger = getDefaultPassenger();
        doReturn(Optional.of(passenger)).when(passengerRepository).findById(id);
        doReturn(getDefaultPassengerResponse()).when(passengerMapper).toPassengerDTO(passenger);
    }

    @When("The getPassengerById method is called with id {long}")
    public void theGetPassengerByIdMethodIsCalledWithId(Long id) {
        try {
            passengerResponse = passengerService.getPassengerById(id);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should contain the passenger with id {long}")
    public void theResponseShouldContainThePassengerWithId(Long id) {
        assertThat(passengerResponse.getId()).isEqualTo(id);
    }

    @Given("A passenger with email {string} and phone number {string} doesn't exist")
    public void aPassengerWithEmailAndPhoneDoesNotExist(String email, String phoneNumber) {
        doReturn(false).when(passengerRepository).existsByEmail(email);
        doReturn(false).when(passengerRepository).existsByPhoneNumber(phoneNumber);
    }

    @When("A addPassenger method is called with name {string}, email {string} and phone number {string}")
    public void aAddPassengerMethodIsCalledWithNameEmailAndPhone(String name, String email, String phoneNumber) {
        PassengerRequest request = new PassengerRequest(name, email, phoneNumber);
        Passenger passenger = Passenger.builder()
                .id(null)
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
        doReturn(passenger).when(passengerMapper).toPassengerEntity(request);
        doReturn(passenger).when(passengerRepository).save(passenger);
        doReturn(new PassengerResponse(1L, name, email, phoneNumber)).when(passengerMapper).toPassengerDTO(passenger);

        passengerResponse = passengerService.addPassenger(request);
    }

    @Then("The response should contain the created passenger with name {string}, email {string} and phone number {string}")
    public void theResponseShouldContainTheCreatedPassengerWithNameEmailAndPhone(String name, String email, String phoneNumber) {
        assertThat(passengerResponse.getName()).isEqualTo(name);
        assertThat(passengerResponse.getEmail()).isEqualTo(email);
        assertThat(passengerResponse.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @When("An updatePassenger method is called with id {long}, name {string}, email {string} and phone number {string}")
    public void anUpdatePassengerMethodIsCalledWithIdEmailAndPhone(Long id, String name, String email, String phoneNumber) {
        PassengerRequest request = new PassengerRequest(name, email, phoneNumber);
        Passenger passenger = getDefaultPassenger();
        doReturn(Optional.of(passenger)).when(passengerRepository).findById(id);
        doReturn(passenger).when(passengerMapper).toPassengerEntity(request);
        doReturn(passenger).when(passengerRepository).save(passenger);
        doReturn(new PassengerResponse(id, name, email, phoneNumber)).when(passengerMapper).toPassengerDTO(passenger);

        passengerResponse = passengerService.updatePassenger(id, request);
    }

    @Then("The response should contain the updated passenger with id {long}, name {string}, email {string} and phone number {string}")
    public void theResponseShouldContainTheUpdatedPassengerWithIdEmailAndPhone(Long id, String name, String email, String phoneNumber) {
        assertThat(passengerResponse.getId()).isEqualTo(id);
        assertThat(passengerResponse.getName()).isEqualTo(name);
        assertThat(passengerResponse.getEmail()).isEqualTo(email);
        assertThat(passengerResponse.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Given("A passenger with id {long} doesn't exist")
    public void aPassengerWithIdDoesNotExist(Long id) {
        doReturn(Optional.empty()).when(passengerRepository).findById(id);
    }

    @When("The id {long} is passed to the deletePassenger method")
    public void theIdIsPassedToTheDeletePassengerMethod(long id) {
        try {
            passengerService.deletePassenger(id);
        } catch (ResourceNotFoundException e) {
            exception = e;
        }
    }

    @Then("The ResourceNotFoundException with id {long} should be thrown")
    public void thePassengerNotFoundExceptionWithIdShouldBeThrown(Long id) {
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
        assertThat(exception.getMessage()).contains("Passenger not found - " + id);
    }
}
