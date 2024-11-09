package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:32 AM
 **/

@Getter
public class State {
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public State(@JsonProperty String name) {
        this.name = name;
    }
}
