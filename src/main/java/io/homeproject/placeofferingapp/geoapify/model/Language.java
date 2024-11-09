package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:25 AM
 **/

@Getter
public class Language {
    private final String isoCode;
    private final String name;
    private final String nameNative;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Language(@JsonProperty("iso_code") String isoCode, @JsonProperty String name, @JsonProperty("name_native") String nameNative) {
        this.isoCode = isoCode;
        this.name = name;
        this.nameNative = nameNative;
    }
}
