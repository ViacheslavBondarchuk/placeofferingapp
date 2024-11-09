package io.homeproject.placeofferingapp.geoapify.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.homeproject.placeofferingapp.geoapify.client.GeoapifyHttpClient;
import io.homeproject.placeofferingapp.geoapify.model.IpInfo;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static io.homeproject.placeofferingapp.constants.Common.ONE_HOUR_IN_MILLIS;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 3:12 PM
 **/

@Service
public final class IpInfoServiceImpl implements IpInfoService {
    private final Cache<String, IpInfo> ipInfoCache;
    private final GeoapifyHttpClient client;

    public IpInfoServiceImpl(GeoapifyHttpClient client) {
        this.ipInfoCache = Caffeine.newBuilder()
                .expireAfterWrite(ONE_HOUR_IN_MILLIS, TimeUnit.MILLISECONDS)
                .build();
        this.client = client;
    }

    @Override
    public IpInfo getIpInfo(String ip) {
        return ipInfoCache.get(ip, client::getIpInfo);
    }
}
