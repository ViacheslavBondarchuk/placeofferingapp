package io.homeproject.placeofferingapp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.core.GrantedAuthority;

/**
 * author: vbondarchuk
 * date: 11/16/2024
 * time: 5:24 PM
 **/

public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }

    @JsonCreator
    public static Role of(String authority) {
        return Role.valueOf(authority);
    }
}
