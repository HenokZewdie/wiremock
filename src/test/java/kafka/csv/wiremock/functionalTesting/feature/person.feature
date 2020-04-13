Feature: Check person DB

  Scenario Outline: just test the DB

    Given A person
    When the api is invoked
    Then the response code is "<status>"
    And returned value is "<is saved>"

    Examples:
      | status | is saved |
      | 200    | saved    |