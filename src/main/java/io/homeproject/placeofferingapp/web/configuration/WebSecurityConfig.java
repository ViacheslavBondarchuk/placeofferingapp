package io.homeproject.placeofferingapp.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * author: vbondarchuk
 * date: 10/24/2024
 * time: 11:22 AM
 **/

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private static final String[] publicUrls = {"/", "/js/**", "/css/**", "/user/register"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(requests -> requests.requestMatchers(publicUrls)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form.loginProcessingUrl("/login")
                        .loginPage("/"))
                .logout((logout) -> logout.clearAuthentication(true)
                        .logoutSuccessUrl("/"))
                .build();
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.builder()
                .username("test")
                .password(encoder.encode("test"))
                .roles("USER")
                .build());
        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
