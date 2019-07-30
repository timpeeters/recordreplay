package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;

import java.net.URL;
import java.util.Optional;

public class ForwardRequestUseCase implements UseCase {

    private final HttpClient httpClient;
    private final URL target;

    public ForwardRequestUseCase(HttpClient httpClient) {
        this(httpClient, null);
    }

    public ForwardRequestUseCase(HttpClient httpClient, URL target) {
        this.httpClient = httpClient;
        this.target = target;
    }

    Response forward(Request request) {
        if (target == null) {
            return httpClient.execute(request);
        }
        return httpClient.execute(toTarget(request));
    }

    @Override
    public Optional<Response> execute(Stub stub) {
        return Optional.of(forward(stub.getRequest()));
    }

    private Request toTarget(Request request) {
        return Request.builder()
                .method(request.getMethod())
                .path(target.toString())
                .headers(request.getHeaders())
                .queryParams(request.getQueryParams())
                .body(request.getBody())
                .build();
    }

}
