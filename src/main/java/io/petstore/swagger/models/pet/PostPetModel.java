package io.petstore.swagger.models.pet;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostPetModel {

    @JsonProperty("id")//propiedades para que se convierta en objeto json
    public Long id;

    @JsonProperty("category")
    public Category category;

    @JsonProperty("name")
    public String name;

    @JsonProperty("photoUrls")
    public List<String> photoUrls;

    @JsonProperty("tags")
    public List<Tag> tags;

    @JsonProperty("status")
    public String status;
}
