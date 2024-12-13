package com.software.modsen.rideservice.component;

import com.software.modsen.rideservice.dto.RideListDto;
import com.software.modsen.rideservice.dto.RideRequest;
import com.software.modsen.rideservice.dto.RideResponse;
import com.software.modsen.rideservice.exception.ResourceNotFoundException;
import com.software.modsen.rideservice.mapper.RideMapper;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.model.enums.Status;
import com.software.modsen.rideservice.repository.RideRepository;
import com.software.modsen.rideservice.service.impl.DefaultRideService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.software.modsen.rideservice.util.RideTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@CucumberContextConfiguration
public class RideServiceComponentTest {
    @Mock
    private RideRepository rideRepository;
    @Mock
    private RideMapper rideMapper;
    @InjectMocks
    private DefaultRideService rideService;

    private List<RideResponse> rideResponseList;
    private RideResponse rideResponse;
    private Exception exception;

    @Given("The ride repository contains multiple rides")
    public void theRideRepositoryContainsMultipleRides() {
        List<Ride> rides = List.of(getDefaultRide(), getUpdatedRide());
        doReturn(rides).when(rideRepository).findAll();

        rideResponseList = List.of(getDefaultRideResponse(), getUpdatedRideResponse());
        for (Ride ride : rides) {
            doReturn(rideResponseList.get((int) (ride.getId() - 1)))
                    .when(rideMapper).toRideDto(ride);
        }
    }

    @When("The getRides method is called")
    public void theGetRidesMethodIsCalled() {
        RideListDto rideListDTO = rideService.getRides();
        rideResponseList = rideListDTO.getRideDtoList();
    }

    @Then("The response should contain a list of all rides")
    public void theResponseShouldContainAListOfAllRides() {
        assertThat(rideResponseList).hasSize(2);
    }

    @Given("A ride with id {long} exists in the repository")
    public void aRideWithIdExistsInTheRepository(Long id) {
        Ride ride = getDefaultRide();
        doReturn(Optional.of(ride)).when(rideRepository).findById(id);
        doReturn(getDefaultRideResponse()).when(rideMapper).toRideDto(ride);
    }

    @When("The getRideById method is called with id {long}")
    public void theGetRideByIdMethodIsCalledWithId(Long id) {
        try {
            rideResponse = rideService.getRideById(id);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should contain the ride with id {long}")
    public void theResponseShouldContainTheRideWithId(Long id) {
        assertThat(rideResponse.getId()).isEqualTo(id);
    }

    @Given("A ride with pickup address {string} and destination address {string} doesn't exist")
    public void aRideWithPickupDestinationDoesNotExist(String pickupAddress, String destinationAddress) {
        doReturn(false).when(rideRepository).existsByPickupAddressAndDestinationAddress(pickupAddress, destinationAddress);
    }

    @When("A addRide method is called with passengerId {long}, driverId {long}, pickup address {string}, destination address {string}, price {double}, and status {string}")
    public void aAddRideMethodIsCalledWithDetails(Long passengerId, Long driverId, String pickupAddress, String destinationAddress, Double price, String status) {
        RideRequest request = RideRequest.builder()
                .passengerId(passengerId)
                .driverId(driverId)
                .pickupAddress(pickupAddress)
                .destinationAddress(destinationAddress)
                .price(price)
                .status(status)
                .build();
        Ride ride = Ride.builder()
                .id(null)
                .passengerId(passengerId)
                .driverId(driverId)
                .pickupAddress(pickupAddress)
                .destinationAddress(destinationAddress)
                .price(price)
                .status(Status.valueOf(status))
                .build();
        doReturn(ride).when(rideMapper).toRideEntity(request);
        doReturn(ride).when(rideRepository).save(ride);
        doReturn(new RideResponse(DEFAULT_ID, passengerId, driverId, pickupAddress, destinationAddress, price, status)).when(rideMapper).toRideDto(ride);

        rideResponse = rideService.addRide(request);
    }

    @Then("The response should contain the created ride with passengerId {long}, driverId {long}, pickup address {string}, destination address {string}, price {double}, and status {string}")
    public void theResponseShouldContainTheCreatedRideWithDetails(Long passengerId, Long driverId, String pickupAddress, String destinationAddress, Double price, String status) {
        assertThat(rideResponse.getPassengerId()).isEqualTo(passengerId);
        assertThat(rideResponse.getDriverId()).isEqualTo(driverId);
        assertThat(rideResponse.getPickupAddress()).isEqualTo(pickupAddress);
        assertThat(rideResponse.getDestinationAddress()).isEqualTo(destinationAddress);
        assertThat(rideResponse.getPrice()).isEqualTo(price);
        assertThat(rideResponse.getStatus()).isEqualTo(status);
    }

    @When("An updateRide method is called with id {long}, passengerId {long}, driverId {long}, pickup address {string}, destination address {string}, price {double}, and status {string}")
    public void anUpdateRideMethodIsCalledWithDetails(Long id, Long passengerId, Long driverId, String pickupAddress, String destinationAddress, Double price, String status) {
        RideRequest request = RideRequest.builder()
                .passengerId(passengerId)
                .driverId(driverId)
                .pickupAddress(pickupAddress)
                .destinationAddress(destinationAddress)
                .price(price)
                .status(status)
                .build();
        Ride ride = getDefaultRide();
        doReturn(Optional.of(ride)).when(rideRepository).findById(id);
        doReturn(ride).when(rideMapper).toRideEntity(request);
        doReturn(ride).when(rideRepository).save(ride);
        doReturn(new RideResponse(id, passengerId, driverId, pickupAddress, destinationAddress, price, status)).when(rideMapper).toRideDto(ride);

        rideResponse = rideService.updateRide(id, request);
    }

    @Then("The response should contain the updated ride with id {long}, passengerId {long}, driverId {long}, pickup address {string}, destination address {string}, price {double}, and status {string}")
    public void theResponseShouldContainTheUpdatedRideWithDetails(Long id, Long passengerId, Long driverId, String pickupAddress, String destinationAddress, Double price, String status) {
        assertThat(rideResponse.getId()).isEqualTo(id);
        assertThat(rideResponse.getPassengerId()).isEqualTo(passengerId);
        assertThat(rideResponse.getDriverId()).isEqualTo(driverId);
        assertThat(rideResponse.getPickupAddress()).isEqualTo(pickupAddress);
        assertThat(rideResponse.getDestinationAddress()).isEqualTo(destinationAddress);
        assertThat(rideResponse.getPrice()).isEqualTo(price);
        assertThat(rideResponse.getStatus()).isEqualTo(status);
    }

    @Given("A ride with id {long} doesn't exist")
    public void aRideWithIdDoesNotExist(Long id) {
        doReturn(Optional.empty()).when(rideRepository).findById(id);
    }

    @When("The id {long} is passed to the deleteRide method")
    public void theIdIsPassedToTheDeleteRideMethod(long id) {
        try {
            rideService.deleteRide(id);
        } catch (ResourceNotFoundException e) {
            exception = e;
        }
    }

    @Then("The ResourceNotFoundException with id {long} should be thrown")
    public void theRideNotFoundExceptionWithIdShouldBeThrown(Long id) {
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
        assertThat(exception.getMessage()).contains("Ride not found - " + id);
    }
}
