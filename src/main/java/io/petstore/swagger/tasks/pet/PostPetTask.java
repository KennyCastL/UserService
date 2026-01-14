package io.petstore.swagger.tasks.pet;

import io.petstore.swagger.models.pet.PostPetModel;
import io.petstore.swagger.questions.pet.BuildPet;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostPetTask implements Task {

    private final String endPoint;
    private final String id;
    private final String nameCategory;
    private final String namePet;

    public PostPetTask(String endPoint, String id, String nameCategory, String namePet) {
        this.endPoint = endPoint;
        this.id = id;
        this.nameCategory = nameCategory;
        this.namePet = namePet;
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        PostPetModel petInfo = actor.asksFor(BuildPet.was(id, nameCategory,namePet));

        actor.attemptsTo(
                Post.to(endPoint).with(
                        requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .body(petInfo)
                )
        );
    }
    public static PostPetTask fromPet(String endPoint, String id, Object nameCategory, String namePet){
        return instrumented(PostPetTask.class, endPoint, id, nameCategory, namePet);
    }
}
