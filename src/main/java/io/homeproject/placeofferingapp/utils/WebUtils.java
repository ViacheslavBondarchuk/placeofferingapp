package io.homeproject.placeofferingapp.utils;

import io.homeproject.placeofferingapp.constants.Headers;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 3:19 PM
 **/

public final class WebUtils {
    private WebUtils() {}

    private static String getRequesterIp(HttpServletRequest request) {
        String xff = request.getHeader(Headers.X_FORWARDED_FOR);
        return xff == null ? request.getRemoteAddr() : xff;
    }

    public static String getRequesterIp() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .map(WebUtils::getRequesterIp)
                .orElseThrow(() -> new RuntimeException("Can not get IP address"));
    }

    public static ClientHttpRequestFactory newPoolingHttpRequestFactory(int connectionPoolSize, long timeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(connectionPoolSize);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new BufferingClientHttpRequestFactory(requestFactory);
    }


}
