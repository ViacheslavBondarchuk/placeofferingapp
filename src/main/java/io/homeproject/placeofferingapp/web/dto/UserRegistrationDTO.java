package io.homeproject.placeofferingapp.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * author: vbondarchuk
 * date: 11/16/2024
 * time: 5:16 PM
 **/

@Getter
@RequiredArgsConstructor
public class UserRegistrationDTO {
    private final String username;
    private final String password;
    private final String repassword;
}
