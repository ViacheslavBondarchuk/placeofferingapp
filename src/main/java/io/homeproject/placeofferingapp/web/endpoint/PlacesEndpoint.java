package io.homeproject.placeofferingapp.web.endpoint;

import io.homeproject.placeofferingapp.geoapify.model.CategoriesGroup;
import io.homeproject.placeofferingapp.geoapify.model.ConditionsGroup;
import io.homeproject.placeofferingapp.geoapify.service.PlacesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 7:24 PM
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public final class PlacesEndpoint implements Endpoint {
    private final PlacesService placesService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoriesGroup>> getCategories() {
        return Endpoint.ok(placesService.getCategoryGroups());
    }

    @GetMapping("/conditions")
    public ResponseEntity<List<ConditionsGroup>> getConditions() {
        return Endpoint.ok(placesService.getConditionGroups());
    }
}
