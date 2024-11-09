package io.homeproject.placeofferingapp.web.endpoint;

import io.homeproject.placeofferingapp.geoapify.service.MapFrameService;
import io.homeproject.placeofferingapp.web.model.MapFrameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static io.homeproject.placeofferingapp.constants.Common.TWENTY_FOUR_HOUR_IN_MILLIS;
import static java.time.temporal.ChronoUnit.MILLIS;

/**
 * author: vbondarchuk
 * date: 10/25/2024
 * time: 9:52 AM
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/frame")
public class MapFrameEndpoint implements Endpoint {
    private final MapFrameService mapFrameService;

    @GetMapping("/{z}/{x}/{y}")
    public ResponseEntity<byte[]> getMapFrame(MapFrameRequest request) {
        return Endpoint.okWithPublicCache(mapFrameService.getMapFrame(request), Duration.of(TWENTY_FOUR_HOUR_IN_MILLIS, MILLIS));
    }
}
