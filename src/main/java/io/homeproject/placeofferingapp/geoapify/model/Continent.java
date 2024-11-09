package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:20 AM
 **/

@Getter
public class Continent {
    private final String code;
    private final String geonameId;
    private final Map<String, String> names;
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Continent(@JsonProperty String code,
                     @JsonProperty("geoname_id") String geonameId,
                     @JsonProperty Map<String, String> names,
                     @JsonProperty String name) {
        this.code = code;
        this.geonameId = geonameId;
        this.names = names;
        this.name = name;
    }
}
