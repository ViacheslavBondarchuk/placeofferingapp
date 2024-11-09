package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:31 AM
 **/

@Getter
public class Subdivision {
    private final Map<String, String> names;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Subdivision(@JsonProperty Map<String, String> names) {
        this.names = names;
    }
}
