package io.petstore.swagger.models.pet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Category {
    @JsonProperty("id")//comportamiento como json
    public Integer id;

    @JsonProperty("name")
    public String name;
}
