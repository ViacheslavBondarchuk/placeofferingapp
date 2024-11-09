package io.homeproject.placeofferingapp.web.endpoint;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Duration;

/**
 * author: vbondarchuk
 * date: 10/16/2024
 * time: 9:36 AM
 **/

public interface Endpoint {

    static <T> ResponseEntity<T> ok(T value) {
        return ResponseEntity.ok(value);
    }

    static <T> ResponseEntity<T> okWithPublicCache(T value, Duration maxAge) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(maxAge).cachePublic())
                .body(value);
    }

    @ExceptionHandler(Throwable.class)
    default ResponseEntity<String> exceptionHandler(Throwable ex) {
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }
}
