package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 4:07 PM
 **/

@RequiredArgsConstructor
public enum LocationType {
    COUNTRY("country"), STATE("state"), CITY("city"), POSTCODE("postcode"), STREET("street"), AMENITY("amenity");

    private static final String EXCEPTION_MESSAGE_TEMPLATE = "Location type {0} is not valid. Supported values: {1}";

    private final String value;

    public String value() {
        return value;
    }

    private static final Map<String, LocationType> mapping = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(LocationType::value, Function.identity()));

    @JsonCreator
    public static LocationType forValue(String value) {
        return Optional.ofNullable(mapping.get(value))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(EXCEPTION_MESSAGE_TEMPLATE, value, mapping.keySet())));
    }

    @Override
    public String toString() {
        return value;
    }
}
