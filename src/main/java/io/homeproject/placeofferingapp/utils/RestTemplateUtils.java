package io.homeproject.placeofferingapp.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 6:30 PM
 **/

public final class RestTemplateUtils {
    public static final Supplier<RuntimeException> extractBodyExceptionSupplier =
            () -> new RuntimeException("Could not extract the body because body is null");

    private RestTemplateUtils() {}

    public static RestTemplate newPoolingRestTemplate(int connectionPoolSize, long timeout, List<ClientHttpRequestInterceptor> interceptors) {
        ClientHttpRequestFactory requestFactory = WebUtils.newPoolingHttpRequestFactory(connectionPoolSize, timeout);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    public static RestTemplate newPoolingRestTemplate(int connectionPoolSize, long timeout) {
        return RestTemplateUtils.newPoolingRestTemplate(connectionPoolSize, timeout, Collections.emptyList());
    }

    public static <T> T fetch(RestTemplate restTemplate, String url, ParameterizedTypeReference<T> typeReference, Object... uriVariables) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeReference, uriVariables);
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(extractBodyExceptionSupplier);
    }

    public static <T> T fetch(RestTemplate restTemplate, String url, ParameterizedTypeReference<T> typeReference, Map<String, ?> uriVariables) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeReference, uriVariables);
        return Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(extractBodyExceptionSupplier);
    }

}
