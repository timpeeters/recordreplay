package be.xplore.recordreplay.repo;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.QueryParams;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.RequestMethod;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.service.HttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;

public class OkHttpClient implements HttpClient {
    private final okhttp3.OkHttpClient client;

    public OkHttpClient() {
        client = new okhttp3.OkHttpClient.Builder().proxySelector(ProxySelector.getDefault()).build();
    }

    private OkHttpClient(Proxy proxy) {
        client = new okhttp3.OkHttpClient.Builder().proxy(proxy).build();
    }

    public static OkHttpClient noProxy() {
        return new OkHttpClient(Proxy.NO_PROXY);
    }

    @Override
    public Response execute(Request request) {
        try (okhttp3.Response response = client.newCall(toOkHttpRequest(request)).execute()) {
            return toResponse(response);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private okhttp3.Request toOkHttpRequest(Request request) {
        RequestBody body = getRequestBody(request);
        URL url = getUrl(request.getPath(), request.getQueryParams());
        return new okhttp3.Request.Builder()
                .method(request.getMethod().name(), body)
                .url(url)
                .headers(okhttp3.Headers.of(request.getHeaders().toVarargs()))
                .build();
    }

    private RequestBody getRequestBody(Request request) {
        if (request.getMethod() == RequestMethod.GET) {
            return null;
        }
        String contentType = request.getHeaders().getModifiableHeaderMap().get("Content-Type").get(0);
        return RequestBody.create(request.getBody(), MediaType.parse(contentType));
    }

    private URL getUrl(String baseUrl, QueryParams params) {
        try {
            return URI.create(baseUrl + params.getQueryString()).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Can't convert url", e);
        }
    }

    private Response toResponse(okhttp3.Response response) {
        return Response.builder()
                .statusCode(response.code())
                .statusText(response.message())
                .body(getResponseBody(response))
                .headers(convertHeaders(response.headers()))
                .build();
    }

    private Headers convertHeaders(okhttp3.Headers headers) {
        return Headers.builder().headerMap(headers.toMultimap()).build();
    }

    private String getResponseBody(okhttp3.Response response) {
        var body = response.body();
        if (body == null) {
            return "";
        }
        try {
            return body.string();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}