package io.homeproject.placeofferingapp.geoapify.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.homeproject.placeofferingapp.geoapify.client.GeoapifyHttpClient;
import io.homeproject.placeofferingapp.web.model.MapFrameRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static io.homeproject.placeofferingapp.constants.Common.FIVE_MINUTES_IN_MILLIS;

/**
 * author: vbondarchuk
 * date: 10/25/2024
 * time: 11:05 AM
 **/

@Service
public final class MapFrameServiceImpl implements MapFrameService {
    private final Cache<MapFrameRequest, byte[]> cache;
    private final GeoapifyHttpClient client;

    public MapFrameServiceImpl(GeoapifyHttpClient client) {
        this.client = client;
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(FIVE_MINUTES_IN_MILLIS, TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public byte[] getMapFrame(MapFrameRequest request) {
        return cache.get(request, client::getMapFrame);
    }
}
