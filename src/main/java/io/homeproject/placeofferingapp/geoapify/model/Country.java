package io.homeproject.placeofferingapp.geoapify.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * author: vbondarchuk
 * date: 11/4/2024
 * time: 7:22 AM
 **/

@Getter
public class Country {
    private final String geonameId;
    private final String iso_code;
    private final Map<String, String> names;
    private final String name;
    private final String nameNative;
    private final String phoneCode;
    private final String capital;
    private final String currency;
    private final String flag;
    private final List<Language> languages;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Country(@JsonProperty("geoname_id") String geonameId,
                   @JsonProperty("iso_code") String isoCode,
                   @JsonProperty Map<String, String> names,
                   @JsonProperty String name,
                   @JsonProperty("name_native") String nameNative,
                   @JsonProperty("phone_code") String phoneCode,
                   @JsonProperty String capital,
                   @JsonProperty String currency,
                   @JsonProperty String flag,
                   @JsonProperty List<Language> languages) {
        this.geonameId = geonameId;
        iso_code = isoCode;
        this.names = names;
        this.name = name;
        this.nameNative = nameNative;
        this.phoneCode = phoneCode;
        this.capital = capital;
        this.currency = currency;
        this.flag = flag;
        this.languages = languages;
    }
}
