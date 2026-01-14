package io.petstore.swagger.stepDefinitions;

import io.cucumber.java.en.*;
import io.petstore.swagger.tasks.user.PostUserTask;
import io.petstore.swagger.utils.ExcelReader;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import static io.petstore.swagger.constants.Constants.URL_BASE_PET;
import java.util.List;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserPostStepDefinition {

    private static final String restAPI = URL_BASE_PET;

    @When("I consume the endpoint @PostUser")
    public void iConsumeTheEndpointPostUser() {
        // Inicializar Serenity Screenplay
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("user").whoCan(CallAnApi.at(restAPI));
    }

    @Given("I send the user information")
    public void iSendTheUserInformation() {
        Actor user = OnStage.theActorCalled("user");
        // Leer Excel
        List<String[]> users = ExcelReader.read(
                "src/test/resources/data/users.xlsx",
                "POST"
        );

        for (int i = 0; i < users.size(); i++) {
            String[] row = users.get(i);

            // Validaciones básicas: omitir filas incompletas o encabezados
            if (row == null || row.length < 9) {
                System.out.println("Fila ignorada (incompleta) index=" + i);
                continue;
            }
            // Si la fila parece un encabezado, ignórala (ajusta si tu Excel no tiene encabezado)
            if ("endpoint".equalsIgnoreCase(row[0]) || "id".equalsIgnoreCase(row[1])) {
                System.out.println("Fila ignorada (encabezado) index=" + i);
                continue;
            }

            String endpoint = row[0] != null ? row[0].trim() : "";
            String id = row[1];
            String userName = row[2];
            String firstName = row[3];
            String lastName = row[4];
            String email = row[5];
            String password = row[6];
            String phone = row[7];
            String userStatus = row[8];

            // Normalizar endpoint para evitar rutas dobles o sin slash
            if (!endpoint.startsWith("/")) {
                endpoint = "/" + endpoint;
            }

            // Ejecutar POST
            user.attemptsTo(
                    PostUserTask.fromUser(endpoint, id, userName, firstName, lastName, email, password, phone, userStatus)
            );

            // Debug: registrar status y body de la última respuesta
            int status = SerenityRest.lastResponse() != null ? SerenityRest.lastResponse().statusCode() : -1;
            String body = SerenityRest.lastResponse() != null ? SerenityRest.lastResponse().getBody().asString() : "null";
            System.out.println("Request to: " + restAPI + endpoint + " -> status: " + status);
            System.out.println("Response body: " + body);

        }
    }

    @Then("I can validate the response of service")
    public void iCanValidateTheResponseOfService() {
        // Esta comprobación puede quedarse si quieres una validación final adicional.
        Actor user = OnStage.theActorCalled("user");

        List<String[]> users = ExcelReader.read(
                "src/test/resources/data/users.xlsx",
                "POST"
        );

        for (String[] row : users) {
            if (row == null || row.length <= 9) continue;
            String code = row[9];
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