package io.homeproject.placeofferingapp.geoapify.service;

import io.homeproject.placeofferingapp.geoapify.model.CategoriesGroup;
import io.homeproject.placeofferingapp.geoapify.model.ConditionsGroup;

import java.util.List;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 7:30 PM
 **/

public interface PlacesService {

    List<CategoriesGroup> getCategoryGroups();

    List<ConditionsGroup> getConditionGroups();


}
