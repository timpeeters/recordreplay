package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.service.Repository;

import java.util.Optional;

public class RecordUseCase implements UseCase {

    private final Repository repository;
    private final ForwardRequestUseCase forward;

    public RecordUseCase(Repository repository, HttpClient client) {
        this.repository = repository;
        this.forward = new ForwardRequestUseCase(client);
    }

    @Override
    public Optional<Response> execute(Stub stub) {
        Response response = forward.forward(stub.getRequest());
        repository.add(new Stub(stub.getRequest(), response));
        return Optional.of(response);
    }

}
