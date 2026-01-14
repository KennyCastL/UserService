Feature: Config User

  @PostUser
  Scenario: Create user successfully
    When I consume the endpoint @PostUser
    Given I send the user information
    Then I can validate the response of service



  @GetUser

  Scenario: User consulted successfully.
    When I consume the endpoint @GetUser
    Given I consult the user information userName
    Then I can validate the response of service <code>


  #@PutUser