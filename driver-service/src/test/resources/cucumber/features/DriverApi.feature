Feature: Driver API End-to-End Testing
  As a user of the Driver service
  I want to verify the functionality of the Driver API
  So that all endpoints work as expected

  Scenario: Successful registration of a driver
    Given the Driver service is running
    And the system has no driver with license number "LICENSE123"
    When I send a POST request to "/drivers/register-and-send-event" with the following data:
      | name       | license_number | rating |
      | John Doe   | LICENSE123     | 5      |
    Then the response status should be 200
    And the response body should contain:
      | name       | license_number | rating |
      | John Doe   | LICENSE123     | 5      |

  Scenario: Fetching profile of an existing driver
    Given the Driver service is running
    And the system has a driver with ID "1"
    When I send a GET request to "/drivers/profile/1"
    Then the response status should be 200
    And the response body should contain:
      | id | name       | license_number | rating |
      | 1  | John Doe   | LICENSE123     | 5      |

  Scenario: Attempt to fetch a non-existent driver
    Given the Driver service is running
    And the system has no driver with ID "999"
    When I send a GET request to "/drivers/profile/999"
    Then the response status should be 404
    And the response body should contain an error message

  Scenario: Edit a driver profile
    Given the Driver service is running
    And the system has a driver with ID "1"
    When I send a PUT request to "/drivers/edit/1" with the following data:
      | name       | license_number | rating |
      | Jane Smith | LICENSE999     | 4      |
    Then the response status should be 200
    And the response body should contain:
      | name       | license_number | rating |
      | Jane Smith | LICENSE999     | 4      |

  Scenario: Delete a driver
    Given the Driver service is running
    And the system has a driver with ID "1"
    When I send a DELETE request to "/drivers/delete/1"
    Then the response status should be 204

  Scenario: Rate a driver
    Given the Driver service is running
    And the system has a driver with ID "1"
    When I send a POST request to "/drivers/rate/1?rating=4"
    Then the response status should be 200
