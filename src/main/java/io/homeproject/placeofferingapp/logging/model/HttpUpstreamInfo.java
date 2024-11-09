package io.homeproject.placeofferingapp.logging.model;

import lombok.Getter;
import lombok.Setter;
import net.logstash.logback.marker.Markers;
import org.slf4j.Marker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 7:57 PM
 **/

@Getter
@Setter
public final class HttpUpstreamInfo {
    private final Direction direction;
    private final String uuid;

    private HttpMethod httpMethod;
    private HttpHeaders headers;
    private URI uri;
    private String body;
    private Integer statusCode;
    private String statusMessage;


    private HttpUpstreamInfo(Direction direction, String uuid) {
        this.direction = direction;
        this.uuid = uuid;
    }

    public Marker toMarker() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("direction", direction);
        map.put("uuid", uuid);
        if (httpMethod != null) {
            map.put("httpMethod", httpMethod.name());
        }
        if (headers != null && !headers.isEmpty()) {
            map.put("headers", headers);
        }
        if (uri != null) {
            map.put("uri", uri);
        }
        if (body != null && !body.isEmpty()) {
            map.put("body", body);
        }
        if (statusCode != null) {
            map.put("statusCode", statusCode);
        }
        if (statusMessage != null) {
            map.put("statusMessage", statusMessage);
        }
        return Markers.appendEntries(map);
    }

    public static HttpUpstreamInfo newRequestInfo(UUID uuid) {
        return new HttpUpstreamInfo(Direction.REQUEST, uuid.toString());
    }

    public static HttpUpstreamInfo newResponseInfo(UUID uuid) {
        return new HttpUpstreamInfo(Direction.RESPONSE, uuid.toString());
    }


}
