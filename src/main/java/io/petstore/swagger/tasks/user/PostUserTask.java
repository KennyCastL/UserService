package io.petstore.swagger.tasks.user;

import io.petstore.swagger.models.user.PostUserModel;
import io.petstore.swagger.questions.user.BuildUser;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostUserTask implements Task {

    private final String endPoint;
    private final String id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;
    private final String userStatus;


    public PostUserTask(String endPoint, String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        this.endPoint = endPoint;
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        PostUserModel userInfo = actor.asksFor(BuildUser.was(id,username,firstName,lastName,email,password,phone,userStatus));

        actor.attemptsTo(
                Post.to(endPoint).with(
                        requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .body(userInfo)
                )
        );
        System.out.println("User creado:");
        System.out.println(userInfo);
    }
    public static PostUserTask fromUser (String endPoint, String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus){
        return instrumented(PostUserTask.class, endPoint, id, username, firstName, lastName, email, password, phone, userStatus);
    }
}