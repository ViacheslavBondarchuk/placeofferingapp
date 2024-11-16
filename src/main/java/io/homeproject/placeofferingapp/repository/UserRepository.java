package io.homeproject.placeofferingapp.repository;

import io.homeproject.placeofferingapp.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * author: vbondarchuk
 * date: 11/16/2024
 * time: 4:04 PM
 **/

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
