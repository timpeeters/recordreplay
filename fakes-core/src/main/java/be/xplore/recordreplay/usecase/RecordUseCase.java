package be.xplore.recordreplay.usecase;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.HttpClient;
import be.xplore.fakes.service.Repository;

import java.util.Optional;

public class RecordUseCase implements UseCase {

    private final Repository repository;
    private final ForwardRequestUseCase forward;

    public RecordUseCase(Repository repository, HttpClient client) {
        this.repository = repository;
        this.forward = new ForwardRequestUseCase(client);
    }

    public Optional<Response> execute(Stub stub) {
        Response response = forward.forward(stub.getRequest());
        repository.add(new Stub(stub.getRequest(), response));
        return Optional.of(response);
    }

}
