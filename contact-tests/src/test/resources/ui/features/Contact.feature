@contact
Feature: Contact page feature

  Background:
    Given User navigate to Contact module
    And User waits for "Cookies" page to load
    And User clicks "Accept All Cookies" button
    And User waits for "Contact" page to load

  @smoke
  Scenario: Contact request page can be filled and submitted
    When User clicks "Request" button
    And User waits for "Contact Request" page to load
    # Check validations
    And User clicks "Submit" button
    Then Ensure mandatory validation message appears for
      | Name, Surname  | Person Code | Document Number | Phone  | Email      | Address  | Meaning | Request Type |
      | x              | x           |                 | x      | x          | x        | x       | x            |
    And User fills contact request data
      | Name, Surname  | Person Code | Document Number | Phone  | Email      | Address  | Meaning | Request Type |
      | Test Person    | 1234567890  |                 | 112233 | wrong      | Test str | Test    | E-pasts      |
    Then Ensure mandatory validation message appears for
      | Name, Surname  | Person Code | Document Number | Phone  | Email      | Address  | Meaning | Request Type |
      |                |             |                 |        | x          |          |         |              |
    And User fills contact request data
      | Name, Surname  | Person Code | Document Number | Phone  | Email      | Address  | Meaning | Request Type |
      | -              | -           | 1               | 112233 | test@email | -        | -       | -            |
    # Check validations are gone and submit
    ### TODO: Uncomment this step instead temporary close after successful submit limitation is over
    #And User clicks "Submit Request" button
    And User clicks "Close Request" button
    Then Ensure "Contact Request" page or area closed

  Scenario: Search box is working
    When User clicks "Search" button
    And User clicks "Close Search" button
    And User clicks "Search" button
    And User fills search criteria "test"
    And User performs searching
    Then User waits for "Search" page to load

  Scenario: Login is working
    When User clicks "Login" button
    Then User waits for "Login" page to load

  Scenario: Cookie settings page is working
    When User clicks "Cookie Settings" button
    Then User waits for "Cookie Settings" page to load
    And User clicks "Confirm My Choises" button
    And Ensure "Cookie Settings" page or area closed

  Scenario: Info Bar appearing and can be closed
    When User clicks "Close Info Bar" button
    Then Ensure "Info Bar" page or area closed
