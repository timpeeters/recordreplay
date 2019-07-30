package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repository.Repository;

import java.net.URL;
import java.util.Optional;

public class RecordUseCase implements UseCase {

    private final Repository repository;
    private final ForwardRequestUseCase forward;

    public RecordUseCase(Repository repository, HttpClient client) {
        this.repository = repository;
        this.forward = new ForwardRequestUseCase(client);
    }

    public RecordUseCase(Repository repository, HttpClient client, URL target) {
        this.repository = repository;
        this.forward = new ForwardRequestUseCase(client, target);
    }

    @Override
    public Optional<Response> execute(Stub stub) {
        Response response = forward.forward(stub.getRequest());
        repository.add(new Stub(stub.getRequest(), response));
        return Optional.of(response);
    }

}
