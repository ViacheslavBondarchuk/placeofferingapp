package io.homeproject.placeofferingapp.geoapify.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.homeproject.placeofferingapp.geoapify.client.GeoapifyHttpClient;
import io.homeproject.placeofferingapp.web.model.ReverseGeocodingRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static io.homeproject.placeofferingapp.constants.Common.ONE_HOUR_IN_MILLIS;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 5:31 PM
 **/

@Service
public class AddressServiceImpl implements AddressService {
    private final GeoapifyHttpClient client;
    private final Cache<ReverseGeocodingRequest, Object> reverseGeocodingCache;

    public AddressServiceImpl(GeoapifyHttpClient client) {
        this.client = client;
        this.reverseGeocodingCache = Caffeine.newBuilder()
                .expireAfterAccess(ONE_HOUR_IN_MILLIS, TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public Object getLocationByReverseGeocoding(ReverseGeocodingRequest request) {
        return reverseGeocodingCache.get(request, client::getLocationByReverseGeocoding);
    }
}
