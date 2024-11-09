package io.homeproject.placeofferingapp.web.endpoint;

import io.homeproject.placeofferingapp.geoapify.service.AddressService;
import io.homeproject.placeofferingapp.web.model.ReverseGeocodingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 5:15 PM
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public final class AddressEndpoint implements Endpoint {
    private final AddressService addressService;

    @PostMapping(path = "/reverse-geocoding")
    public Object reverseGeocoding(@Validated @RequestBody ReverseGeocodingRequest request) {
        return Endpoint.ok(addressService.getLocationByReverseGeocoding(request));
    }
}
