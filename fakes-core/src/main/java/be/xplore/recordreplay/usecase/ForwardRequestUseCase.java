package be.xplore.recordreplay.usecase;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.recordreplay.service.HttpClient;

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
