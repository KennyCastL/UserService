package io.petstore.swagger.questions.user;

import io.petstore.swagger.models.user.GetUserModel;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class GetUser implements Question<GetUserModel> {

    private final String  username;

    public GetUser(String username) {
        this.username = username;
    }


    @Override
    public GetUserModel answeredBy(Actor actor) {

        GetUserModel userDataGet = GetUserModel.builder()
                .username(username)
                .build();

        return userDataGet;

    }
    public static GetUser wasGet(String username) {
        return new GetUser(username);
    }


}
