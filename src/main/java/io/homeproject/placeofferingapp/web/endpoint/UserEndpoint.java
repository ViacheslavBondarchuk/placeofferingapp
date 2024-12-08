package io.homeproject.placeofferingapp.web.endpoint;

import io.homeproject.placeofferingapp.service.UserService;
import io.homeproject.placeofferingapp.web.dto.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * author: vbondarchuk
 * date: 11/16/2024
 * time: 5:08 PM
 **/


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserEndpoint implements Endpoint {
//    private final UserService userService;

    @PostMapping("/register")
    public RedirectView register(@ModelAttribute UserRegistrationDTO dto) {
//        userService.save(dto);
        return new RedirectView("/?success=true");
    }
}
