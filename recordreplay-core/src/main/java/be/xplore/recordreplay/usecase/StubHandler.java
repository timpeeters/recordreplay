package be.xplore.recordreplay.usecase;

import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;

import java.util.Optional;

public class StubHandler {
    private final UseCase useCase;

    public StubHandler(UseCase useCase) {
        this.useCase = useCase;
    }

    public Optional<Response> handle(Stub stub) {
        return useCase.execute(stub);
    }
}
