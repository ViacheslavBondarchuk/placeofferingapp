package io.homeproject.placeofferingapp.geoapify.client;

import io.homeproject.placeofferingapp.geoapify.model.CategoriesGroup;
import io.homeproject.placeofferingapp.geoapify.model.ConditionsGroup;
import io.homeproject.placeofferingapp.geoapify.model.IpInfo;
import io.homeproject.placeofferingapp.web.model.MapFrameRequest;
import io.homeproject.placeofferingapp.web.model.ReverseGeocodingRequest;

import java.util.List;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 5:32 PM
 **/

public interface GeoapifyHttpClient {
    IpInfo getIpInfo(String ip);

    List<CategoriesGroup> getCategoryGroups();

    List<ConditionsGroup> getConditionGroups();

    Object getLocationByReverseGeocoding(ReverseGeocodingRequest request);

    byte[] getMapFrame(MapFrameRequest request);

    List<Object> getPlaces();
}
