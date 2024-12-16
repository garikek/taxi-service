Feature: Driver Rating Service

  Scenario: Getting all driver ratings
    Given The driver rating repository contains multiple ratings
    When The getDriverRatings method is called
    Then The response should contain a list of all driver ratings

  Scenario: Getting a driver rating by ID
    Given A driver rating with id 1 exists in the repository
    When The getDriverRatingById method is called with id 1
    Then The response should contain the driver rating with id 1

  Scenario: Adding a new driver rating
    Given A driver rating with driver id 1 doesn't exist
    When A addDriverRating method is called with driverId 1 and rating 4.5
    Then The response should contain the created driver rating with driverId 1 and rating 4.5

  Scenario: Updating a driver rating by ID
    Given A driver rating with id 1 exists in the repository
    When An updateDriverRating method is called with id 1, driverId 2 and rating 4.9
    Then The response should contain the updated driver rating with id 1, driverId 2 and rating 4.9

  Scenario: Deleting a driver rating by non-existing ID
    Given A driver rating with id 999 doesn't exist
    When The id 999 is passed to the deleteDriverRating method
    Then The ResourceNotFoundException with id 999 should be thrown
