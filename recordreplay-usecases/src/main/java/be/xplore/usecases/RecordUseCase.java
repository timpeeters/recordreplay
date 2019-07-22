package be.xplore.usecases;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Repository;
import be.xplore.recordreplay.service.OkHttpClient;

import java.util.Optional;

public class RecordUseCase {

    private final Repository repository;
    private final ForwardRequestUseCase forward;

    public RecordUseCase(Repository repository) {
        this.repository = repository;
        forward = new ForwardRequestUseCase(new OkHttpClient());
    }

    public Optional<Response> execute(Stub stub) {
        Response response = forward.execute(stub);
        repository.add(new Stub(stub.getRequest(), response));
        return Optional.of(response);
    }

}
