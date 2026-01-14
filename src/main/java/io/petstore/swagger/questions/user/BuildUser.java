package io.petstore.swagger.questions.user;

import io.petstore.swagger.models.user.PostUserModel;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;


public class BuildUser implements Question<PostUserModel> {

    private final String id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;
    private final String userStatus;

    public BuildUser(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {

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
    public PostUserModel answeredBy(Actor actor) {

        PostUserModel userData = PostUserModel.builder()
                .id(Integer.valueOf(id))
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(Integer.valueOf(userStatus))
                .build();

        return userData;

    }
    public static BuildUser was(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        return new BuildUser(id,username,firstName,lastName,email,password,phone,userStatus);
    }


}
