package be.xplore.usecases;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Repository;

import java.util.Optional;

public class RecordUseCase {

    private final Repository repository;

    public RecordUseCase(Repository repository) {
        this.repository = repository;
    }

    public Optional<Response> execute(Stub stub) {
        repository.add(stub);
        return  Optional.of(stub.getResponse());
    }

}
