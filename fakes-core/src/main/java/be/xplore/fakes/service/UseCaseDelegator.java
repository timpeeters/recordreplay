package be.xplore.fakes.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;

import java.util.Optional;

public class UseCaseDelegator {
    private UseCase useCase;

    public UseCaseDelegator(UseCase useCase) {
        this.useCase = useCase;
    }

    public Optional<Response> handle(Stub stub) {
        return useCase.execute(stub);
    }
}
