Feature: Create Pet

  @PostPet
  Scenario Outline: Create pet successfully.
    When I consume the endpoint "<endpoint>" and I send the user information id "<id>", nameCategory"<nameCategory>",namePet"<namePet>"
    Then I can validate the response service <code>


    Examples:

      | id | nameCategory | namePet | endpoint | code |
      | 1  | Dog          | doggie  | /pet     | 200  |