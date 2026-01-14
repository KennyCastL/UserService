package io.petstore.swagger.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)//la clase se vuelve ejecutable
@CucumberOptions(
        features = "src/test/resources/features/",
        tags = "@PostPet",
        glue = "io.petstore.swagger.stepDefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)

public class Runner {
}
