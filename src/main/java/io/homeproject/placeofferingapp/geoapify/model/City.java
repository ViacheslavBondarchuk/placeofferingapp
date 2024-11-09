package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:18 AM
 **/

@Getter
public class City {
    private final Map<String, String> names;
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public City(@JsonProperty Map<String, String> names, @JsonProperty String name) {
        this.names = names;
        this.name = name;
    }
}
