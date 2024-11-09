package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:18 AM
 **/

@Getter
public class IpInfo {
    private final City city;
    private final Continent continent;
    private final Country country;
    private final Location location;
    private final List<Subdivision> subdivisions;
    private final State state;
    private final List<Datasource> datasource;
    private final String ip;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public IpInfo(@JsonProperty City city,
                  @JsonProperty Continent continent,
                  @JsonProperty Country country,
                  @JsonProperty Location location,
                  @JsonProperty List<Subdivision> subdivisions,
                  @JsonProperty State state,
                  @JsonProperty List<Datasource> datasource,
                  @JsonProperty String ip) {
        this.city = city;
        this.continent = continent;
        this.country = country;
        this.location = location;
        this.subdivisions = subdivisions;
        this.state = state;
        this.datasource = datasource;
        this.ip = ip;
    }
}
