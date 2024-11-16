package io.homeproject.placeofferingapp.web.model;

import java.util.Set;

/**
 * author: vbondarchuk
 * date: 11/16/2024
 * time: 6:16 PM
 **/


public record PlacesRequest(String type, String coordinates, Set<String> categories, long limit) {
}
