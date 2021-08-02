Feature: ERS Login and Request Process

  Scenario: User is on ERS home page and login with wrong credentials.
    Given User is on ERS home page
    When User enters the wrong credentials
    And User clicks the submit button
    Then User is redirected to the ERS home page

  Scenario: User is on ERS home page and login with correct credentials.
    Given User is on ERS home page
    When User enters the correct credentials
    And User clicks the submit button
    Then User is redirected to the employee dashboard page

  Scenario: User is on ERS home page and login with correct credentials.
    Given User is on ERS home page
    When User enters the correct credentials
    And User hits the enter button to login
    Then User is redirected to the employee dashboard page

  Scenario: User submit a first request on employee dashboard page.
    When User submit first request on employee dashboard page
    And User clicks the submit button
    Then User is redirected to the employee dashboard page

  Scenario: User submit a second request on employee dashboard page.
    When User submit second request on employee dashboard page
    And User hits the enter button to post request
    Then User is redirected to the employee dashboard page

  Scenario: User clicks the log out button on employee dashboard page.
    When User clicks the log out button on employee dashboard page
    Then User is redirected to the ERS homepage

  Scenario: User is on ERS home page and login with correct manager credentials.
    When User enters the correct manager credentials
    And User hits the enter button to login
    Then User is redirected to the manager dashboard page

  Scenario: User approves first request on manager dashboard page.
    When User approves first request on manager dashboard page
    Then User is redirected to the manager dashboard page

  Scenario: User denies second request on manager dashboard page.
    When User denies second request on manager dashboard page
    Then User is redirected to the manager dashboard page

  Scenario: User clicks the log out button on manager dashboard page.
    When User clicks the log out button on manager dashboard page
    Then User is redirected to the ERS homepage