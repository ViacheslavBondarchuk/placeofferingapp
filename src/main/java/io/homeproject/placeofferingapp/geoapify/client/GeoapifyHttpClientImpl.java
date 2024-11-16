package io.homeproject.placeofferingapp.geoapify.client;

import io.homeproject.placeofferingapp.geoapify.configuration.GeoapifyConfiguration;
import io.homeproject.placeofferingapp.geoapify.model.AbstractInfoGroup;
import io.homeproject.placeofferingapp.geoapify.model.CategoriesGroup;
import io.homeproject.placeofferingapp.geoapify.model.CategoryFilterInfo;
import io.homeproject.placeofferingapp.geoapify.model.ConditionsGroup;
import io.homeproject.placeofferingapp.geoapify.model.IpInfo;
import io.homeproject.placeofferingapp.geoapify.model.LocationType;
import io.homeproject.placeofferingapp.geoapify.provider.GeoApifyKeyProvider;
import io.homeproject.placeofferingapp.logging.interceptor.HttpRequestLoggingInterceptor;
import io.homeproject.placeofferingapp.utils.RestTemplateUtils;
import io.homeproject.placeofferingapp.web.model.MapFrameRequest;
import io.homeproject.placeofferingapp.web.model.ReverseGeocodingRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 5:32 PM
 **/

@Component
public final class GeoapifyHttpClientImpl implements GeoapifyHttpClient {
    public static final Comparator<AbstractInfoGroup> infoGroupByKeyComparator = Comparator.comparing(AbstractInfoGroup::getKey);

    private static final String IP_KEY_NAME = "ip";
    private static final String API_KEY_NAME = "key";
    private static final String LAT_KEY_NAME = "lat";
    private static final String LOT_KEY_NAME = "lon";
    private static final String LIMIT_KEY_NAME = "limit";
    private static final String TYPE_KEY_NAME = "type";
    private static final String LANG_KEY_NAME = "lang";
    private static final String DEFAULT_LANG_KEY_NAME = "en";
    private static final String X_KEY_NAME = "x";
    private static final String Y_KEY_NAME = "y";
    private static final String Z_KEY_NAME = "z";
    private static final String CATEGORIES = "categories";
    private static final String FILTER = "filter";

    private final GeoApifyKeyProvider apiKeyProvider;
    private final RestTemplate restTemplate;
    private final GeoapifyConfiguration.EndpointConfiguration endpointConfiguration;

    public GeoapifyHttpClientImpl(GeoapifyConfiguration configuration, GeoApifyKeyProvider apiKeyProvider) {
        this.endpointConfiguration = configuration.endpoint();
        this.apiKeyProvider = apiKeyProvider;
        this.restTemplate = RestTemplateUtils.newPoolingRestTemplate(
                configuration.connection().poolsize(), configuration.connection().timeout(), List.of(new HttpRequestLoggingInterceptor()));
    }

    private String getCategoryKey(String category) {
        String[] keys = category.split("\\.");
        return keys[0];
    }

    private Map<String, Set<String>> toFilterMap(List<String> collection) {
        return collection.stream()
                .collect(Collectors.groupingBy(GeoapifyHttpClientImpl.this::getCategoryKey, Collectors.toCollection(TreeSet::new)));
    }

    private Map<String, ?> createReverseGeocodingUriVariables(ReverseGeocodingRequest request) {
        return Map.of(
                API_KEY_NAME, apiKeyProvider.getApiKey(),
                LAT_KEY_NAME, request.latitude(),
                LOT_KEY_NAME, request.longitude(),
                LIMIT_KEY_NAME, request.limit() == null ? 1 : request.limit(),
                LANG_KEY_NAME, request.lang() == null ? DEFAULT_LANG_KEY_NAME : request.lang(),
                TYPE_KEY_NAME, request.type() == null ? LocationType.CITY.value() : request.type().value()
        );
    }

    private Map<String, ?> createMapFrameUri(MapFrameRequest request) {
        return Map.of(
                Z_KEY_NAME, request.z(),
                X_KEY_NAME, request.x(),
                Y_KEY_NAME, request.y(),
                API_KEY_NAME, apiKeyProvider.getApiKey()
        );
    }

    @Override
    public IpInfo getIpInfo(String ip) {
        Map<String, String> uriVariables = Map.of(IP_KEY_NAME, ip, API_KEY_NAME, apiKeyProvider.getApiKey());
        return RestTemplateUtils.fetch(restTemplate, endpointConfiguration.ipinfo(), new ParameterizedTypeReference<>() {}, uriVariables);
    }

    @Override
    public List<CategoriesGroup> getCategoryGroups() {
        List<CategoryFilterInfo> infos = RestTemplateUtils.fetch(restTemplate, endpointConfiguration.placemaincategories(), new ParameterizedTypeReference<>() {});
        List<String> categories = RestTemplateUtils.fetch(restTemplate, endpointConfiguration.placecategories(),new ParameterizedTypeReference<>() {});
        Map<String, Set<String>> filterMap = toFilterMap(categories);
        return infos.stream()
                .map(info -> new CategoriesGroup(info.getKey(), info.getIcon(), info.getInfo(), filterMap.get(info.getKey())))
                .sorted(infoGroupByKeyComparator)
                .toList();
    }

    @Override
    public List<ConditionsGroup> getConditionGroups() {
        List<CategoryFilterInfo> infos = RestTemplateUtils.fetch(restTemplate, endpointConfiguration.placemainconditions(), new ParameterizedTypeReference<>() {});
        List<String> conditions = RestTemplateUtils.fetch(restTemplate, endpointConfiguration.placeconditions(), new ParameterizedTypeReference<>() {});
        Map<String, Set<String>> filterMap = toFilterMap(conditions);
        return infos.stream()
                .map(info -> new ConditionsGroup(info.getKey(), info.getIcon(), info.getInfo(), filterMap.get(info.getKey())))
                .sorted(infoGroupByKeyComparator)
                .toList();
    }

    @Override
    public Object getLocationByReverseGeocoding(ReverseGeocodingRequest request) {
        Map<String, ?> uriVariables = createReverseGeocodingUriVariables(request);
        return RestTemplateUtils.fetch(restTemplate, endpointConfiguration.reversegeocode(), new ParameterizedTypeReference<>() {}, uriVariables);
    }

    @Override
    public byte[] getMapFrame(MapFrameRequest request) {
        Map<String, ?> uriVariables = createMapFrameUri(request);
        String url = request.retina() ? endpointConfiguration.leafletretinaurl() : endpointConfiguration.leafletbaseurl();
        return RestTemplateUtils.fetch(restTemplate, url, new ParameterizedTypeReference<>() {}, uriVariables);
    }

    @Override
    public List<Object> getPlaces() {
        //categories={categories}&filter={filter}&limit={limit}&apiKey={key}


        return List.of();
    }

}
