package be.xplore.recordreplay.usecase;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;

import java.util.Optional;

public class StubHandler {
    private static final Object MUTEX = new Object();
    private static StubHandler currentConfig;
    private final UseCase useCase;

    public StubHandler(UseCase useCase) {
        this.useCase = useCase;
    }

    public static StubHandler getCurrent() {
        synchronized (MUTEX) {
            return currentConfig;
        }
    }

    public static void setCurrent(StubHandler stubHandler) {
        synchronized (MUTEX) {
            currentConfig = stubHandler;
        }
    }

    public Optional<Response> handle(Stub stub) {
        return useCase.execute(stub);
    }
}
