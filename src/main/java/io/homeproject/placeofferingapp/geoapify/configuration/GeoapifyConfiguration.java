package io.homeproject.placeofferingapp.geoapify.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 5:37 PM
 **/

@ConfigurationProperties("api.geoapify")
public record GeoapifyConfiguration(EndpointConfiguration endpoint, ConnectionConfiguration connection) {
    public record EndpointConfiguration(String ipinfo,
                                        String placeconditions,
                                        String placecategories,
                                        String placemainconditions,
                                        String placemaincategories,
                                        String reversegeocode,
                                        String leafletbaseurl,
                                        String leafletretinaurl) {

        public EndpointConfiguration(String ipinfo,
                                     String placeconditions,
                                     String placecategories,
                                     String placemainconditions,
                                     String placemaincategories,
                                     String reversegeocode,
                                     String leafletbaseurl,
                                     String leafletretinaurl) {
            this.ipinfo = Objects.requireNonNull(ipinfo, "geoapify endpoint ipinfo cannot be null");
            this.placeconditions = Objects.requireNonNull(placeconditions, "geoapify endpoint placeconditions cannot be null");
            this.placecategories = Objects.requireNonNull(placecategories, "geoapify endpoint placecategories cannot be null");
            this.placemainconditions = Objects.requireNonNull(placemainconditions, "geoapify endpoint placemainconditions cannot be null");
            this.placemaincategories = Objects.requireNonNull(placemaincategories, "geoapify endpoint placemaincategories cannot be null");
            this.reversegeocode = Objects.requireNonNull(reversegeocode, "geoapify endpoint reversegeocode cannot be null");
            this.leafletbaseurl = Objects.requireNonNull(leafletbaseurl, "geoapify endpoint leafletbaseurl cannot be null");
            this.leafletretinaurl = Objects.requireNonNull(leafletretinaurl, "geoapify endpoint leafletretinaurl cannot be null");
        }
    }

    public record ConnectionConfiguration(Integer poolsize, Integer timeout) {

        public ConnectionConfiguration(Integer poolsize, Integer timeout) {
            this.poolsize = Objects.requireNonNull(poolsize, "geoapify poolsize cannot be null");
            this.timeout = Objects.requireNonNull(timeout, "geoapify timeout cannot be null");
        }
    }
}
