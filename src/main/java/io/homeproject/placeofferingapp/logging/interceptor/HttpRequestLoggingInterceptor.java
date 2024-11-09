package io.homeproject.placeofferingapp.logging.interceptor;

import io.homeproject.placeofferingapp.logging.model.HttpUpstreamInfo;
import io.homeproject.placeofferingapp.logging.service.LoggingService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.homeproject.placeofferingapp.logging.service.LoggingService.upstreamLogger;

/**
 * author: vbondarchuk
 * date: 10/15/2024
 * time: 7:56 PM
 **/

public final class HttpRequestLoggingInterceptor implements ClientHttpRequestInterceptor {

    private String readResponseBody(ClientHttpResponse response) {
        String body = null;
        try {
            InputStream inputStream = response.getBody();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            body = bufferedReader.lines()
                    .collect(Collectors.joining());
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
        return body;
    }

    private HttpUpstreamInfo newRequestHttpUpstreamInfo(HttpRequest request, HttpMethod httpMethod, URI uri, byte[] body, UUID uuid) {
        HttpUpstreamInfo httpUpstreamRequest = HttpUpstreamInfo.newRequestInfo(uuid);
        httpUpstreamRequest.setHttpMethod(httpMethod);
        httpUpstreamRequest.setHeaders(request.getHeaders());
        httpUpstreamRequest.setUri(uri);
        httpUpstreamRequest.setBody(new String(body));
        return httpUpstreamRequest;
    }

    private HttpUpstreamInfo newResponseHttpUpstreamInfo(ClientHttpResponse response, HttpMethod httpMethod, URI uri, UUID uuid) {
        HttpUpstreamInfo httpUpstreamRequest = HttpUpstreamInfo.newResponseInfo(uuid);
        httpUpstreamRequest.setHttpMethod(httpMethod);
        httpUpstreamRequest.setHeaders(response.getHeaders());
        httpUpstreamRequest.setUri(uri);
        httpUpstreamRequest.setBody(readResponseBody(response));
        return httpUpstreamRequest;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        UUID uuid = UUID.randomUUID();
        HttpMethod httpMethod = request.getMethod();
        URI uri = request.getURI();

        HttpUpstreamInfo httpUpstreamRequestInfo = newRequestHttpUpstreamInfo(request, httpMethod, uri, body, uuid);
        LoggingService.info(upstreamLogger, httpUpstreamRequestInfo.toMarker());
        ClientHttpResponse clientHttpResponse = execution.execute(request, body);
        HttpUpstreamInfo httpUpstreamResponseInfo = newResponseHttpUpstreamInfo(clientHttpResponse, httpMethod, uri, uuid);
        LoggingService.info(upstreamLogger, httpUpstreamResponseInfo.toMarker());
        return clientHttpResponse;
    }
}
