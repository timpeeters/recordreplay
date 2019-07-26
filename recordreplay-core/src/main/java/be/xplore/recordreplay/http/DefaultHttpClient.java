package be.xplore.recordreplay.http;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DefaultHttpClient implements be.xplore.recordreplay.http.HttpClient {

    private final HttpClient client;

    public DefaultHttpClient() {
        client = HttpClient.newHttpClient();
    }

    public DefaultHttpClient(String proxyHost, int proxyPort) {
        client = HttpClient.newBuilder()
                .proxy(ProxySelector.of(InetSocketAddress.createUnresolved(proxyHost, proxyPort)))
                .build();
    }

    @Override
    public Response execute(Request request) {
        HttpRequest httpRequest = httpRequestBuilder(request);
        HttpResponse<String> httpResponse = getHttpResponse(httpRequest);
        return responseBuilder(httpResponse);
    }

    private HttpRequest httpRequestBuilder(Request request) {
        return HttpRequest
                .newBuilder()
                .method(request.getMethod().name(),
                        HttpRequest.BodyPublishers.ofString(request.getBody()))
                .uri(request.toUri())
                .headers(request.getHeaders().toVarargs())
                .build();
    }

    private HttpResponse<String> getHttpResponse(HttpRequest httpRequest) {
        try {
            return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not send http-request", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Current thread is interrupted", e);
        }
    }

    private Response responseBuilder(HttpResponse<String> httpResponse) {
        return Response.builder()
                .statusCode(httpResponse.statusCode())
                .headers(new Headers(httpResponse.headers()))
                .body(httpResponse.body())
                .build();
    }
}

