Feature: Passenger Rating Service

  Scenario: Getting all passenger ratings
    Given The passenger rating repository contains multiple ratings
    When The getPassengerRatings method is called
    Then The response should contain a list of all passenger ratings

  Scenario: Getting a passenger rating by ID
    Given A passenger rating with id 1 exists in the repository
    When The getPassengerRatingById method is called with id 1
    Then The response should contain the passenger rating with id 1

  Scenario: Adding a new passenger rating
    Given A passenger rating with passenger id 1 doesn't exist
    When A addPassengerRating method is called with passengerId 1 and rating 4.5
    Then The response should contain the created passenger rating with passengerId 1 and rating 4.5

  Scenario: Updating a passenger rating by ID
    Given A passenger rating with id 1 exists in the repository
    When An updatePassengerRating method is called with id 1, passengerId 2 and rating 4.9
    Then The response should contain the updated passenger rating with id 1, passengerId 2 and rating 4.9

  Scenario: Deleting a passenger rating by non-existing ID
    Given A passenger rating with id 999 doesn't exist
    When The id 999 is passed to the deletePassengerRating method
    Then The ResourceNotFoundException with id 999 should be thrown
