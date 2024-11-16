package io.homeproject.placeofferingapp.mongo.converter;

import io.homeproject.placeofferingapp.domain.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;

/**
 * author: vbondarchuk
 * date: 11/16/2024
 * time: 5:41 PM
 **/

public final class AuthorityConverter implements Converter<String, GrantedAuthority> {

    @Override
    public GrantedAuthority convert(String str) {
        return Role.of(str);
    }
}
