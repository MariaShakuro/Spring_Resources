Feature: Driver Management

  Scenario: Register a new driver
    Given a driver with the following details:
      | name       | licenseNumber | rating |
      | John Doe   | LICENSE123    | 5      |
    When the driver registers
    Then the driver should be saved
    And an event should be sent to the Kafka topic
