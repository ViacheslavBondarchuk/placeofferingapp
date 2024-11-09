package io.homeproject.placeofferingapp.geoapify.service;

import io.homeproject.placeofferingapp.web.model.MapFrameRequest;

/**
 * author: vbondarchuk
 * date: 10/25/2024
 * time: 11:05 AM
 **/

public interface MapFrameService {
    byte[] getMapFrame(MapFrameRequest request);
}
