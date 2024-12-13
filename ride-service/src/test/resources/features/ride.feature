Feature: Ride Service

  Scenario: Getting all rides
    Given The ride repository contains multiple rides
    When The getRides method is called
    Then The response should contain a list of all rides

  Scenario: Getting a ride by ID
    Given A ride with id 1 exists in the repository
    When The getRideById method is called with id 1
    Then The response should contain the ride with id 1

  Scenario: Adding a new ride
    Given A ride with pickup address "Vitebsk, frunze, 123-1" and destination address "Vitebsk, lazo, 125" doesn't exist
    When A addRide method is called with passengerId 1, driverId 1, pickup address "Vitebsk, frunze, 123-1", destination address "Vitebsk, lazo, 125", price 25.0, and status "REQUESTED"
    Then The response should contain the created ride with passengerId 1, driverId 1, pickup address "Vitebsk, frunze, 123-1", destination address "Vitebsk, lazo, 125", price 25.0, and status "REQUESTED"

  Scenario: Updating a ride by ID
    Given A ride with id 1 exists in the repository
    When An updateRide method is called with id 1, passengerId 2, driverId 2, pickup address "Minsk, frunze, 123-1", destination address "Minsk, lazo, 125", price 30.0, and status "COMPLETED"
    Then The response should contain the updated ride with id 1, passengerId 2, driverId 2, pickup address "Minsk, frunze, 123-1", destination address "Minsk, lazo, 125", price 30.0, and status "COMPLETED"

  Scenario: Deleting a ride by non-existing ID
    Given A ride with id 999 doesn't exist
    When The id 999 is passed to the deleteRide method
    Then The ResourceNotFoundException with id 999 should be thrown
