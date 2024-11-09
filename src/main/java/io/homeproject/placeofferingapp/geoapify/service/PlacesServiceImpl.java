package io.homeproject.placeofferingapp.geoapify.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.homeproject.placeofferingapp.geoapify.client.GeoapifyHttpClient;
import io.homeproject.placeofferingapp.geoapify.model.CategoriesGroup;
import io.homeproject.placeofferingapp.geoapify.model.ConditionsGroup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.homeproject.placeofferingapp.constants.Common.TWENTY_FOUR_HOUR_IN_MILLIS;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 7:30 PM
 **/

@Service
public final class PlacesServiceImpl implements PlacesService {
    private static final String CATEGORIES_KEY = "categories";
    private static final String CONDITIONS_KEY = "conditions";

    private final Cache<String, List<CategoriesGroup>> categoriesCache;
    private final Cache<String, List<ConditionsGroup>> conditionsCache;
    private final GeoapifyHttpClient client;

    public PlacesServiceImpl(GeoapifyHttpClient client) {
        this.categoriesCache = Caffeine.newBuilder()
                .expireAfterWrite(TWENTY_FOUR_HOUR_IN_MILLIS, TimeUnit.MILLISECONDS)
                .build();
        this.conditionsCache = Caffeine.newBuilder()
                .expireAfterWrite(TWENTY_FOUR_HOUR_IN_MILLIS, TimeUnit.MILLISECONDS)
                .build();
        this.client = client;
    }

    @Override
    public List<CategoriesGroup> getCategoryGroups() {
        return categoriesCache.get(CATEGORIES_KEY, ignored -> client.getCategoryGroups());
    }

    @Override
    public List<ConditionsGroup> getConditionGroups() {
        return conditionsCache.get(CONDITIONS_KEY, ignored -> client.getConditionGroups());
    }
}
