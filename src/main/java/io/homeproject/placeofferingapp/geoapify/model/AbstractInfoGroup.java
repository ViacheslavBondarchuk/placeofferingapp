package io.homeproject.placeofferingapp.geoapify.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 3:22 PM
 **/

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public sealed abstract class AbstractInfoGroup permits CategoriesGroup, ConditionsGroup {
    protected final String key;
    protected final String icon;
    protected final String description;
    protected final Set<String> filters;
}
