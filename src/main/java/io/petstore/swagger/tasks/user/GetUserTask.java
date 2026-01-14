package io.petstore.swagger.tasks.user;


import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;


import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetUserTask implements Task {

    private final String endPoint;
    private final String username;



    public GetUserTask(String endPoint, String username) {
        this.endPoint = endPoint;
        this.username = username;

    }
    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                Get.resource(endPoint)
                        .with(request -> request
                                .pathParam("userName", username)
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)

                )
        );

    }
    public static GetUserTask fromUser (String endPoint, String username){
        return instrumented(GetUserTask.class, endPoint, username);
    }
}
