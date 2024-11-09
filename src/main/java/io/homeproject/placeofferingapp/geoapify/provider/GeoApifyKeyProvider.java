package io.homeproject.placeofferingapp.geoapify.provider;

import io.homeproject.placeofferingapp.provider.ApiKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 3:14 PM
 **/

@Component
public final class GeoApifyKeyProvider implements ApiKeyProvider {
    private final String key;

    public GeoApifyKeyProvider(@Value("${api.geoapify.key}") String key) {
        this.key = key;
    }

    @Override
    public String getApiKey() {
        return key;
    }
}
