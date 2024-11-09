package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 1:43 PM
 **/


public final class ConditionFilterInfo extends AbstractFilterInfo {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ConditionFilterInfo(@JsonProperty String key, @JsonProperty String info, @JsonProperty String icon) {
        super(key, info, icon);
    }
}
