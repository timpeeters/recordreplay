package be.xplore.fakes.usecase;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.Repository;

public class RecordUseCase {

    private final Repository repository;

    public RecordUseCase(Repository repository) {
        this.repository = repository;
    }

    public Stub record(Request request, Response response) {
        Stub recordedStub = new Stub(request,response);
        repository.add(recordedStub);
        return recordedStub;
    }

}
