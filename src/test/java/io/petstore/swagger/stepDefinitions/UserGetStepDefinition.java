package io.petstore.swagger.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.petstore.swagger.tasks.user.GetUserTask;
import io.petstore.swagger.utils.ExcelReader;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import java.util.List;
import static io.petstore.swagger.constants.Constants.URL_BASE_PET;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserGetStepDefinition {
    private static final String restAPI = URL_BASE_PET;

    @When("I consume the endpoint @GetUser")
    public void iConsumeTheEndpointGetUser() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("user").whoCan(CallAnApi.at(restAPI));
    }

    @Given("I consult the user information userName")
    public void iConsultTheUserInformationUserName() {
        Actor user = OnStage.theActorCalled("user");
        List<String[]> users = ExcelReader.read(
                "src/test/resources/data/users.xlsx",
                "GET"
        );

        for (int i = 0; i < users.size(); i++) {
            String[] row = users.get(i);

            if (row == null || row.length < 2) {
                System.out.println("Fila ignorada (incompleta) index=" + i);
                continue;
            }
            if ("endpoint".equalsIgnoreCase(row[0]) || "userName".equalsIgnoreCase(row[1])) {
                System.out.println("Fila ignorada (encabezado) index=" + i);
                continue;
            }

            String endpoint = row[0] != null ? row[0].trim() : "";
            String userName = row[1] != null ? row[1].trim() : "";
            if (userName.isEmpty()) {
                System.out.println("Fila ignorada (username vacío) index=" + i);
                continue;
            }

            // Normalizar base y crear placeholder /endpoint/{userName}
            if (!endpoint.startsWith("/")) {
                endpoint = "/" + endpoint;
            }
            // eliminar slash final(s)
            while (endpoint.endsWith("/")) {
                endpoint = endpoint.substring(0, endpoint.length() - 1);
            }
            String endpointWithPlaceholder = endpoint + "/{userName}";

            user.attemptsTo(
                    GetUserTask.fromUser(endpointWithPlaceholder, userName)
            );

            int status = SerenityRest.lastResponse() != null ? SerenityRest.lastResponse().statusCode() : -1;
            String body = SerenityRest.lastResponse() != null ? SerenityRest.lastResponse().getBody().asString() : "null";
            System.out.println("Request to: " + restAPI + endpointWithPlaceholder.replace("{userName}", userName) + " -> status: " + status);
            System.out.println("Response body: " + body);
        }
    }

    @Then("I can validate the response of service <code>")
    public void iCanValidateTheResponseOfServiceCode() {
        Actor user = OnStage.theActorCalled("user");

        List<String[]> users = ExcelReader.read(
                "src/test/resources/data/users.xlsx",
                "GET"
        );

        for (String[] row : users) {
            if (row == null || row.length <= 2) continue;
            String code = row[2];
            try {
                int expected = Integer.parseInt(code);
                user.should(
                        seeThat("The response code is",
                                act -> SerenityRest.lastResponse().statusCode(),
                                equalTo(expected)
                        )
                );
            } catch (NumberFormatException e) {
                System.out.println("Código esperado inválido: " + code);
            }
        }
    }
}



