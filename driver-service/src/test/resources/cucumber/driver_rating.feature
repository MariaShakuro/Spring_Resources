Feature: Driver Rating

  Scenario: Rate a driver
    Given a driver with ID 1 exists
    When the driver is rated with a value of 4
    Then the driver's rating should be updated to 4
