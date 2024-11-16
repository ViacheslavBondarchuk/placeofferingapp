package io.homeproject.placeofferingapp.service;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.homeproject.placeofferingapp.domain.Role;
import io.homeproject.placeofferingapp.domain.User;
import io.homeproject.placeofferingapp.repository.UserRepository;
import io.homeproject.placeofferingapp.web.dto.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * author: vbondarchuk
 * date: 11/16/2024
 * time: 4:02 PM
 **/

@Service
@RequiredArgsConstructor
public final class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @CanIgnoreReturnValue
    public User save(UserRegistrationDTO dto) {
        return userRepository.save(User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .authorities(Collections.singleton(Role.USER))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
