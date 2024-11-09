package io.homeproject.placeofferingapp.geoapify.service;

import io.homeproject.placeofferingapp.web.model.ReverseGeocodingRequest;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 5:30 PM
 **/

public interface AddressService {

    Object getLocationByReverseGeocoding(ReverseGeocodingRequest request);
}
