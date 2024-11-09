package io.homeproject.placeofferingapp;

import io.homeproject.placeofferingapp.geoapify.configuration.GeoapifyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        GeoapifyConfiguration.class
})
public class PlaceOfferingApp {

    public static void main(String[] args) {
        SpringApplication.run(PlaceOfferingApp.class, args);
    }

}
