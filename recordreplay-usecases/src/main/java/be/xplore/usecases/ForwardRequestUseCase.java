package be.xplore.usecases;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.HttpClient;
import be.xplore.recordreplay.service.OkHttpClient;

public class ForwardRequestUseCase {

    private final HttpClient httpClient;

    public ForwardRequestUseCase(){
        this.httpClient = new OkHttpClient();
    }

    public ForwardRequestUseCase(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    Response execute(Stub stub) {
        return httpClient.execute(stub.getRequest());
    }

}
