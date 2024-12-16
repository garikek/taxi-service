Feature: Driver Service

  Scenario: Getting all drivers
    Given The driver repository contains multiple drivers
    When The getDrivers method is called
    Then The response should contain a list of all drivers

  Scenario: Getting a driver by ID
    Given A driver with id 1 exists in the repository
    When The getDriverById method is called with id 1
    Then The response should contain the driver with id 1

  Scenario: Adding a new driver
    Given A driver with email "a@mail.com", phone number "+375291234567" and vehicle number "1234AB1" doesn't exist
    When A addDriver method is called with name "a", email "a@mail.com", phone number "+375291234567", and vehicle number "1234AB1"
    Then The response should contain the created driver with name "a", email "a@mail.com", phone number "+375291234567" and vehicle number "1234AB1"

  Scenario: Updating a driver by ID
    Given A driver with id 1 exists in the repository
    When An updateDriver method is called with id 1, name "b", email "b@mail.com", phone "+375292345678", and vehicle number "1234AB2"
    Then The response should contain the updated driver with id 1, name "b", email "b@mail.com", phone number "+375292345678" and vehicle number "1234AB2"

  Scenario: Deleting a driver by non-existing ID
    Given A driver with id 999 doesn't exist
    When The id 999 is passed to the deleteDriver method
    Then The ResourceNotFoundException with id 999 should be thrown
