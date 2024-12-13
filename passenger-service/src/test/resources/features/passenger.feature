Feature: Passenger Service
  Scenario: Getting all passengers
    Given The passenger repository contains multiple passengers
    When The getPassengers method is called
    Then The response should contain a list of all passengers

  Scenario: Getting a passenger by ID
    Given A passenger with id 1 exists in the repository
    When The getPassengerById method is called with id 1
    Then The response should contain the passenger with id 1

  Scenario: Adding a new passenger
    Given A passenger with email "a@mail.com" and phone number "+375291234567" doesn't exist
    When A addPassenger method is called with name "a", email "a@mail.com" and phone number "+375291234567"
    Then The response should contain the created passenger with name "a", email "a@mail.com" and phone number "+375291234567"

  Scenario: Updating a passenger by ID
    Given A passenger with id 1 exists in the repository
    When An updatePassenger method is called with id 1, name "b", email "b@mail.com" and phone number "+375292345678"
    Then The response should contain the updated passenger with id 1, name "b", email "b@mail.com" and phone number "+375292345678"

  Scenario: Deleting a passenger by non-existing ID
    Given A passenger with id 999 doesn't exist
    When The id 999 is passed to the deletePassenger method
    Then The ResourceNotFoundException with id 999 should be thrown
