package io.homeproject.placeofferingapp.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.homeproject.placeofferingapp.geoapify.model.LocationType;
import jakarta.validation.constraints.NotBlank;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 4:05 PM
 **/

public record ReverseGeocodingRequest(
        @NotBlank(message = "longitude can not be blank") String longitude,
        @NotBlank(message = "latitude can not be blank") String latitude,
        Long limit,
        String lang,
        LocationType type
) {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ReverseGeocodingRequest {}
}
