package com.software.modsen.ratingservice.component.passenger;

import com.software.modsen.ratingservice.dto.response.PassengerRatingResponseList;
import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.exception.ResourceNotFoundException;
import com.software.modsen.ratingservice.mapper.PassengerRatingMapper;
import com.software.modsen.ratingservice.model.PassengerRating;
import com.software.modsen.ratingservice.repository.PassengerRatingRepository;
import com.software.modsen.ratingservice.service.impl.PassengerRatingServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.software.modsen.ratingservice.util.PassengerRatingTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@CucumberContextConfiguration
public class PassengerRatingServiceComponentTest {
    @Mock
    private PassengerRatingRepository passengerRatingRepository;
    @Mock
    private PassengerRatingMapper passengerRatingMapper;
    @InjectMocks
    private PassengerRatingServiceImpl passengerRatingService;

    private List<PassengerRatingResponse> passengerRatingResponseList;
    private PassengerRatingResponse passengerRatingResponse;
    private Exception exception;

    @Given("The passenger rating repository contains multiple ratings")
    public void thePassengerRatingRepositoryContainsMultipleRatings() {
        List<PassengerRating> ratings = List.of(getDefaultPassengerRating(), getUpdatedPassengerRating());
        doReturn(ratings).when(passengerRatingRepository).findAll();

        passengerRatingResponseList = List.of(getDefaultPassengerRatingResponse(), getUpdatedPassengerRatingResponse());
        for (PassengerRating rating : ratings) {
            doReturn(passengerRatingResponseList.get((int) (rating.getId() - 1)))
                    .when(passengerRatingMapper).toPassengerRatingDto(rating);
        }
    }

    @When("The getPassengerRatings method is called")
    public void theGetPassengerRatingsMethodIsCalled() {
        PassengerRatingResponseList responseList = passengerRatingService.getPassengerRatings();
        passengerRatingResponseList = responseList.getPassengerRatingResponseList();
    }

    @Then("The response should contain a list of all passenger ratings")
    public void theResponseShouldContainAListOfAllPassengerRatings() {
        assertThat(passengerRatingResponseList).hasSize(2);
    }

    @Given("A passenger rating with id {long} exists in the repository")
    public void aPassengerRatingWithIdExistsInTheRepository(Long id) {
        PassengerRating rating = getDefaultPassengerRating();
        doReturn(Optional.of(rating)).when(passengerRatingRepository).findById(id);
        doReturn(getDefaultPassengerRatingResponse()).when(passengerRatingMapper).toPassengerRatingDto(rating);
    }

    @When("The getPassengerRatingById method is called with id {long}")
    public void theGetPassengerRatingByIdMethodIsCalledWithId(Long id) {
        try {
            passengerRatingResponse = passengerRatingService.getPassengerRatingById(id);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should contain the passenger rating with id {long}")
    public void theResponseShouldContainThePassengerRatingWithId(Long id) {
        assertThat(passengerRatingResponse.getId()).isEqualTo(id);
    }

    @Given("A passenger rating with passenger id {long} doesn't exist")
    public void aPassengerRatingWithPassengerIdDoesNotExist(Long passengerId) {
        doReturn(false).when(passengerRatingRepository).existsByPassengerId(passengerId);
    }

    @When("A addPassengerRating method is called with passengerId {long} and rating {double}")
    public void aAddPassengerRatingMethodIsCalledWithDetails(Long passengerId, Double rating) {
        PassengerRatingRequest request = PassengerRatingRequest.builder()
                .passengerId(passengerId)
                .rating(rating)
                .build();
        PassengerRating passengerRating = PassengerRating.builder()
                .id(null)
                .passengerId(passengerId)
                .rating(rating)
                .build();
        doReturn(passengerRating).when(passengerRatingMapper).toPassengerRatingEntity(request);
        doReturn(passengerRating).when(passengerRatingRepository).save(passengerRating);
        doReturn(new PassengerRatingResponse(DEFAULT_ID, passengerId, rating)).when(passengerRatingMapper).toPassengerRatingDto(passengerRating);

        passengerRatingResponse = passengerRatingService.addPassengerRating(request);
    }

    @Then("The response should contain the created passenger rating with passengerId {long} and rating {double}")
    public void theResponseShouldContainTheCreatedPassengerRatingWithDetails(Long passengerId, Double rating) {
        assertThat(passengerRatingResponse.getPassengerId()).isEqualTo(passengerId);
        assertThat(passengerRatingResponse.getRating()).isEqualTo(rating);
    }

    @When("An updatePassengerRating method is called with id {long}, passengerId {long} and rating {double}")
    public void anUpdatePassengerRatingMethodIsCalledWithDetails(Long id, Long passengerId, Double rating) {
        PassengerRatingRequest request = PassengerRatingRequest.builder()
                .passengerId(passengerId)
                .rating(rating)
                .build();
        PassengerRating passengerRating = getDefaultPassengerRating();
        doReturn(Optional.of(passengerRating)).when(passengerRatingRepository).findById(id);
        doReturn(passengerRating).when(passengerRatingMapper).toPassengerRatingEntity(request);
        doReturn(passengerRating).when(passengerRatingRepository).save(passengerRating);
        doReturn(new PassengerRatingResponse(id, passengerId, rating)).when(passengerRatingMapper).toPassengerRatingDto(passengerRating);

        passengerRatingResponse = passengerRatingService.updatePassengerRating(id, request);
    }

    @Then("The response should contain the updated passenger rating with id {long}, passengerId {long} and rating {double}")
    public void theResponseShouldContainTheUpdatedPassengerRatingWithDetails(Long id, Long passengerId, Double rating) {
        assertThat(passengerRatingResponse.getId()).isEqualTo(id);
        assertThat(passengerRatingResponse.getPassengerId()).isEqualTo(passengerId);
        assertThat(passengerRatingResponse.getRating()).isEqualTo(rating);
    }

    @Given("A passenger rating with id {long} doesn't exist")
    public void aPassengerRatingWithIdDoesNotExist(Long id) {
        doReturn(Optional.empty()).when(passengerRatingRepository).findById(id);
    }

    @When("The id {long} is passed to the deletePassengerRating method")
    public void theIdIsPassedToTheDeletePassengerRatingMethod(long id) {
        try {
            passengerRatingService.deletePassengerRating(id);
        } catch (ResourceNotFoundException e) {
            exception = e;
        }
    }

    @Then("The ResourceNotFoundException with id {long} should be thrown")
    public void thePassengerRatingNotFoundExceptionWithIdShouldBeThrown(Long id) {
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
        assertThat(exception.getMessage()).contains("Passenger rating not found. id - " + id);
    }
}
