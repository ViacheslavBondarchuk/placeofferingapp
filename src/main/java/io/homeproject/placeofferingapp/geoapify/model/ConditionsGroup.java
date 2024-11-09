package io.homeproject.placeofferingapp.geoapify.model;

import java.util.Set;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 3:24 PM
 **/

public final class ConditionsGroup extends AbstractInfoGroup {
    public ConditionsGroup(String key, String icon, String description, Set<String> filters) {
        super(key, icon, description, filters);
    }
}
