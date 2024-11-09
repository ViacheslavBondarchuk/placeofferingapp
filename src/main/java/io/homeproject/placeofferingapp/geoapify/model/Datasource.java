package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:33 AM
 **/

@Getter
public class Datasource {
    private final String name;
    private final String attribution;
    private final String license;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Datasource(@JsonProperty String name, @JsonProperty String attribution, @JsonProperty String license) {
        this.name = name;
        this.attribution = attribution;
        this.license = license;
    }
}
