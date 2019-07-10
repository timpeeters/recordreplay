package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DefaultHttpClient implements be.xplore.fakes.service.HttpClient {

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public Response execute(Request request) {
        HttpRequest httpRequest = httpRequestBuilder(request);
        HttpResponse httpResponse = getHttpResponse(httpRequest);
        return responseBuilder(httpResponse);
    }

    private HttpRequest httpRequestBuilder(Request request) {
        return HttpRequest
                .newBuilder()
                .method(request.getMethod().name(),
                        HttpRequest.BodyPublishers.ofString(request.getBody()))
                .uri(uriBuilder(request))
                .headers(headersBuilder(request))
                .build();
    }

    private URI uriBuilder(Request request) {
        StringBuilder uri = new StringBuilder(request.getPath()).append('?');
        request.getParams()
                .forEach((key, value) -> request.getParams().get(key)
                        .forEach(s -> uri
                                .append(key)
                                .append('=')
                                .append(s)
                                .append('&')));
        return URI.create(uri.deleteCharAt(uri.length()).toString());
    }

    private String[] headersBuilder(Request request) {
        List<String> keyValueList = new ArrayList<>();
        request.getHeaders()
                .forEach((key, value) -> value
                        .forEach(s -> {
                            keyValueList.add(key);
                            keyValueList.add(s);
                        }));
        return keyValueList.toArray(String[]::new);
    }

    private HttpResponse getHttpResponse(HttpRequest httpRequest) {
        try {
            return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not send http-request", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Current thread is interrupted", e);
        }
    }

    private Response responseBuilder(HttpResponse httpResponse) {
        return new Response().setStatusCode(httpResponse.statusCode())
                .setStatusText(httpResponse.body().toString());
    }
}

