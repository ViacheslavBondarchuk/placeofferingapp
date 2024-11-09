package io.homeproject.placeofferingapp.geoapify.model;

import java.util.Set;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 1:55 PM
 **/


public final class CategoriesGroup extends AbstractInfoGroup {
    public CategoriesGroup(String key, String icon, String description, Set<String> filters) {
        super(key, icon, description, filters);
    }
}
