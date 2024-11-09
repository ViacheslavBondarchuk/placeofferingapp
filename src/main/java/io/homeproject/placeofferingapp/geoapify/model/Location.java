package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:29 AM
 **/

@Getter
public class Location {
    private final String latitude;
    private final String longitude;

    @JsonCreator(mode = PROPERTIES)
    public Location(@JsonProperty String latitude, @JsonProperty String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
