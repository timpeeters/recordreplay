package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.http.HttpClient;

import java.util.Optional;

public class ForwardRequestUseCase implements UseCase{

    private final HttpClient httpClient;

    public ForwardRequestUseCase(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    Response forward(Request request) {
        return httpClient.execute(request);
    }

    @Override
    public Optional<Response> execute(Stub stub) {
        return Optional.of(forward(stub.getRequest()));
    }

}
