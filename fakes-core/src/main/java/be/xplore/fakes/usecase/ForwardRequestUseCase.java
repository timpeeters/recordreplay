package be.xplore.fakes.usecase;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.HttpClient;

public class ForwardRequestUseCase {

    private final HttpClient httpClient;

    public ForwardRequestUseCase(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Response execute(Request request) {
        return httpClient.execute(request);
    }

}
