package be.xplore.usecases;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.HttpClient;

public class ForwardRequestUseCase {

    private final HttpClient httpClient;

    public ForwardRequestUseCase(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Response execute(Stub stub) {
        return httpClient.execute(stub.getRequest());
    }

}
