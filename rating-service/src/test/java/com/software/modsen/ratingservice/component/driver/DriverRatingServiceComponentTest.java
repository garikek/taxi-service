package com.software.modsen.ratingservice.component.driver;

import com.software.modsen.ratingservice.dto.response.DriverRatingResponseList;
import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.exception.ResourceNotFoundException;
import com.software.modsen.ratingservice.mapper.DriverRatingMapper;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
import com.software.modsen.ratingservice.service.impl.DriverRatingServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.software.modsen.ratingservice.util.DriverRatingTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@CucumberContextConfiguration
public class DriverRatingServiceComponentTest {
    @Mock
    private DriverRatingRepository driverRatingRepository;
    @Mock
    private DriverRatingMapper driverRatingMapper;
    @InjectMocks
    private DriverRatingServiceImpl driverRatingService;

    private List<DriverRatingResponse> driverRatingResponseList;
    private DriverRatingResponse driverRatingResponse;
    private Exception exception;

    @Given("The driver rating repository contains multiple ratings")
    public void theDriverRatingRepositoryContainsMultipleRatings() {
        List<DriverRating> ratings = List.of(getDefaultDriverRating(), getUpdatedDriverRating());
        doReturn(ratings).when(driverRatingRepository).findAll();

        driverRatingResponseList = List.of(getDefaultDriverRatingResponse(), getUpdatedDriverRatingResponse());
        for (DriverRating rating : ratings) {
            doReturn(driverRatingResponseList.get((int) (rating.getId() - 1)))
                    .when(driverRatingMapper).toDriverRatingDto(rating);
        }
    }

    @When("The getDriverRatings method is called")
    public void theGetDriverRatingsMethodIsCalled() {
        DriverRatingResponseList responseList = driverRatingService.getDriverRatings();
        driverRatingResponseList = responseList.getDriverRatingResponseList();
    }

    @Then("The response should contain a list of all driver ratings")
    public void theResponseShouldContainAListOfAllDriverRatings() {
        assertThat(driverRatingResponseList).hasSize(2);
    }

    @Given("A driver rating with id {long} exists in the repository")
    public void aDriverRatingWithIdExistsInTheRepository(Long id) {
        DriverRating rating = getDefaultDriverRating();
        doReturn(Optional.of(rating)).when(driverRatingRepository).findById(id);
        doReturn(getDefaultDriverRatingResponse()).when(driverRatingMapper).toDriverRatingDto(rating);
    }

    @When("The getDriverRatingById method is called with id {long}")
    public void theGetDriverRatingByIdMethodIsCalledWithId(Long id) {
        try {
            driverRatingResponse = driverRatingService.getDriverRatingById(id);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should contain the driver rating with id {long}")
    public void theResponseShouldContainTheDriverRatingWithId(Long id) {
        assertThat(driverRatingResponse.getId()).isEqualTo(id);
    }

    @Given("A driver rating with driver id {long} doesn't exist")
    public void aDriverRatingWithDriverIdDoesNotExist(Long driverId) {
        doReturn(false).when(driverRatingRepository).existsByDriverId(driverId);
    }

    @When("A addDriverRating method is called with driverId {long} and rating {double}")
    public void aAddDriverRatingMethodIsCalledWithDetails(Long driverId, Double rating) {
        DriverRatingRequest request = DriverRatingRequest.builder()
                .driverId(driverId)
                .rating(rating)
                .build();
        DriverRating driverRating = DriverRating.builder()
                .id(null)
                .driverId(driverId)
                .rating(rating)
                .build();
        doReturn(driverRating).when(driverRatingMapper).toDriverRatingEntity(request);
        doReturn(driverRating).when(driverRatingRepository).save(driverRating);
        doReturn(new DriverRatingResponse(DEFAULT_ID, driverId, rating)).when(driverRatingMapper).toDriverRatingDto(driverRating);

        driverRatingResponse = driverRatingService.addDriverRating(request);
    }

    @Then("The response should contain the created driver rating with driverId {long} and rating {double}")
    public void theResponseShouldContainTheCreatedDriverRatingWithDetails(Long driverId, Double rating) {
        assertThat(driverRatingResponse.getDriverId()).isEqualTo(driverId);
        assertThat(driverRatingResponse.getRating()).isEqualTo(rating);
    }

    @When("An updateDriverRating method is called with id {long}, driverId {long} and rating {double}")
    public void anUpdateDriverRatingMethodIsCalledWithDetails(Long id, Long driverId, Double rating) {
        DriverRatingRequest request = DriverRatingRequest.builder()
                .driverId(driverId)
                .rating(rating)
                .build();
        DriverRating driverRating = getDefaultDriverRating();
        doReturn(Optional.of(driverRating)).when(driverRatingRepository).findById(id);
        doReturn(driverRating).when(driverRatingMapper).toDriverRatingEntity(request);
        doReturn(driverRating).when(driverRatingRepository).save(driverRating);
        doReturn(new DriverRatingResponse(id, driverId, rating)).when(driverRatingMapper).toDriverRatingDto(driverRating);

        driverRatingResponse = driverRatingService.updateDriverRating(id, request);
    }

    @Then("The response should contain the updated driver rating with id {long}, driverId {long} and rating {double}")
    public void theResponseShouldContainTheUpdatedDriverRatingWithDetails(Long id, Long driverId, Double rating) {
        assertThat(driverRatingResponse.getId()).isEqualTo(id);
        assertThat(driverRatingResponse.getDriverId()).isEqualTo(driverId);
        assertThat(driverRatingResponse.getRating()).isEqualTo(rating);
    }

    @Given("A driver rating with id {long} doesn't exist")
    public void aDriverRatingWithIdDoesNotExist(Long id) {
        doReturn(Optional.empty()).when(driverRatingRepository).findById(id);
    }

    @When("The id {long} is passed to the deleteDriverRating method")
    public void theIdIsPassedToTheDeleteDriverRatingMethod(long id) {
        try {
            driverRatingService.deleteDriverRating(id);
        } catch (ResourceNotFoundException e) {
            exception = e;
        }
    }

    @Then("The ResourceNotFoundException with id {long} should be thrown")
    public void theDriverRatingNotFoundExceptionWithIdShouldBeThrown(Long id) {
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
        assertThat(exception.getMessage()).contains("Driver rating not found - " + id);
    }
}
